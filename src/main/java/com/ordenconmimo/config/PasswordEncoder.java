package com.ordenconmimo.config;

import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class PasswordEncoder {
    
    public String encode(String rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }
    
    public boolean matches(String rawPassword, String encodedPassword) {
        String encodedRaw = encode(rawPassword);
        return encodedRaw.equals(encodedPassword);
    }
}