package com.ua.hackathon2024java.web.temporary;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inspector {
    private long counter = 0;
    private Long id;
    private String email;
    private String password;
    private List<String> regions;

    public Inspector(String email, String password, List<String> regions) {
        this.id = ++counter;
        this.email = email;
        this.password = password;
        this.regions = regions;
    }
}
