package com.ua.hackathon2024java.entity;

import lombok.Getter;

@Getter
public enum ReportStatus {
    PENDING("На розгляді"),
    IN_PROGRESS("В процесі"),
    REJECTED("Відхилено"),
    ACCEPTED("Прийнято");

    private final String label;

    ReportStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static ReportStatus fromLabel(String label) {
        for (ReportStatus status : values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + label);
    }
}