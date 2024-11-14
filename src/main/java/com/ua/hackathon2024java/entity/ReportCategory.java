package com.ua.hackathon2024java.entity;

import lombok.Getter;

@Getter
public enum ReportCategory {
    CORRUPTION("Корупція"),
    ABUSE_OF_POWER("Зловживання владою"),
    FRAUD("Шахрайство"),
    OTHER("Інше");

    private final String label;

    ReportCategory(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label;
    }

    public static ReportCategory fromLabel(String label) {
        for (ReportCategory category : values()) {
            if (category.getLabel().equalsIgnoreCase(label)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown label: " + label);
    }
}
