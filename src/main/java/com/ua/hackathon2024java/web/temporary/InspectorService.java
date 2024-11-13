package com.ua.hackathon2024java.web.temporary;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InspectorService {
    private final List<Inspector> inspectors = new ArrayList<Inspector>();

    public List<Inspector> getInspectors() {
        return inspectors;
    }

    public void createInspector(String email, String password, List<String> regions) {
        Inspector inspector = new Inspector(email, password, regions);
        inspectors.add(inspector);
    }

    public void updateInspectorRegions(Long id,  List<String> regions) {
        inspectors.stream()
                .filter(inspector -> inspector.getId().equals(id))
                .findFirst()
                .ifPresent(inspector -> inspector.setRegions(regions));
    }
    public void deleteInspector(Long id) {
        inspectors.removeIf(inspector -> inspector.getId().equals(id));
    }
}
