package com.ordenconmimo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.main.web-application-type=none" // Evita iniciar el servidor web
})
class OrdenconmimoApplicationTest {

    @Test
    void contextLoads() {
        // Simplemente verifica que el contexto de Spring se carga correctamente
        // Sin iniciar el servidor web
    }
}