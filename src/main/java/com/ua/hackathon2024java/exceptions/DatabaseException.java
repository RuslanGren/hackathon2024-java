package com.ua.hackathon2024java.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Database error occurred!")
public class DatabaseException extends RuntimeException {
}