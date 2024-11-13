package com.ua.hackathon2024java.entity;

import com.ua.hackathon2024java.exceptions.BadRequestException;

public enum Regions {
    REGION_KYIV,
    REGION_LVIV,
    REGION_ODESA,
    REGION_DNIPRO,
    REGION_KHARKIV,
    REGION_ZAPORIZHZHIA,
    REGION_VINNYTSIA,
    REGION_POLTAVA,
    REGION_CHERNIHIV,
    REGION_IVANO_FRANKIVSK,
    REGION_LUHANSK,
    REGION_DONETSK,
    REGION_RIVNE,
    REGION_KHERSON,
    REGION_KHMELNYTSKYI,
    REGION_CHERNIVTSI,
    REGION_SUMY,
    REGION_ZHYTOMYR,
    REGION_CHERKASY,
    REGION_MYKOLAIV,
    REGION_TERNOPIL,
    REGION_VOLYNSKA,
    REGION_ZAKARPATSKA;

    public static boolean exists(String region) {
        for (Regions r : Regions.values()) {
            if (r.name().equals(region)) {
                return true;
            }
        }
        return false;
    }

    public static Regions getRegion(String region) {
        if (exists(region)) {
            return Regions.valueOf(region);
        } else {
            throw new BadRequestException("Invalid region: " + region);
        }
    }
}
