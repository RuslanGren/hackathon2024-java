//package com.ua.hackathon2024java.web.googledrive;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//import com.google.gson.Gson;
//import com.google.gson.stream.JsonReader;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class GoogleDriveUploader {
//
//    private static final String APPLICATION_NAME = "ReportAgregation";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//
//    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
//    private static final String CREDENTIALS_FILE_PATH = "/resources/hackaton2024-441518-374988660e76.json";
//
//    private Drive driveService;
//
//    public GoogleDriveUploader() throws GeneralSecurityException, IOException {
//        try {
//            initializeGoogleDriveService();
//        } catch (GeneralSecurityException | IOException e) {
//            throw new RuntimeException("Failed to initialize Google Drive service", e);
//        }
//    }
//
//    private void initializeGoogleDriveService() throws GeneralSecurityException, IOException {
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        this.driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//        InputStream in = GoogleDriveUploader.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new IOException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public void uploadFile(byte[] fileData, String fileName) throws IOException {
//        File fileMetadata = new File();
//        fileMetadata.setName(fileName);
//
//        com.google.api.services.drive.model.File file = driveService.files().create(fileMetadata,
//                        new com.google.api.client.http.ByteArrayContent("application/pdf", fileData))
//                .setFields("id")
//                .execute();
//
//        System.out.println("File ID: " + file.getId());
//    }
//}
