package com.ua.hackathon2024java.services.impl;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.services.drive.model.File;


import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "ReportAgregation";

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES =
            Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    public void updateFolderPermissionsForLink(String folderId) {
        try {
            // Створюємо об'єкт дозволу для публічного доступу
            Permission permission = new Permission()
                    .setType("anyone")  // Доступ для всіх
                    .setRole("reader")  // Можна встановити "writer" або "reader"
                    .setAllowFileDiscovery(false);  // Встановити true, якщо хочете, щоб цей доступ був видимим в пошуку Google Drive

            // Додаємо або оновлюємо дозволи
            getInstance().permissions().create(folderId, permission).execute();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8081).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }
    public Drive getInstance() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
    public String uploadFile(MultipartFile file, String folderId) {
        try {
            if (file != null && !file.isEmpty()) {
                File fileMetadata = new File();
                if (folderId != null && !folderId.isEmpty()) {
                    fileMetadata.setParents(Collections.singletonList(folderId));
                }
                fileMetadata.setName(file.getOriginalFilename());

                InputStreamContent mediaContent = new InputStreamContent(
                        file.getContentType(),
                        new ByteArrayInputStream(file.getBytes())
                );

                File uploadFile = getInstance()
                        .files()
                        .create(fileMetadata, mediaContent)
                        .setFields("id")
                        .execute();

                return uploadFile.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createFolder(String folderName, String parentFolderId) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(folderName);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            if (parentFolderId != null && !parentFolderId.isEmpty()) {
                fileMetadata.setParents(Collections.singletonList(parentFolderId));
            }

            File folder = getInstance().files().create(fileMetadata).setFields("id").execute();
            return folder.getId();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
