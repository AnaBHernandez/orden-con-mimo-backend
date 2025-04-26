package com.ordenconmimo.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PasswordEncoderTest {

    private com.ordenconmimo.config.PasswordEncoder passwordEncoder = new com.ordenconmimo.config.PasswordEncoder();
    
    @Test
    public void testEncode() {
        // Dado
        String rawPassword = "password123";
        
        // Cuando
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Entonces
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
    }
    
    @Test
    public void testMatches() {
        // Dado
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Cuando
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        boolean doesNotMatch = passwordEncoder.matches("wrongpassword", encodedPassword);
        
        // Entonces
        assertTrue(matches, "La contraseña original debe coincidir con la codificada");
        assertFalse(doesNotMatch, "Una contraseña incorrecta no debe coincidir");
    }
    
    @Test
    public void testEncodeEmptyPassword() {
        // Dado
        String rawPassword = "";
        
        // Cuando
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Entonces
        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Debe coincidir con la cadena vacía");
    }
    
    @Test
    public void testEncodeSpecialCharacters() {
        // Dado
        String rawPassword = "p@$$w0rd!";
        
        // Cuando
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Entonces
        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Debe coincidir con caracteres especiales");
    }
}