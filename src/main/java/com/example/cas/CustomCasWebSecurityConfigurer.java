package com.example.cas;

import org.apereo.cas.web.CasWebSecurityConfigurer;

import java.util.List;

public class CustomCasWebSecurityConfigurer implements CasWebSecurityConfigurer {

    @Override
    public List<String> getIgnoredEndpoints() {
        return List.of("/public/**");
    }
}
