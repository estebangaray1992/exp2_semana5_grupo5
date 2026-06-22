package com.minimarket.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VentaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
            username = "cliente",
            roles = {
                    "CLIENTE"
            }
    )
    void clienteDebePoderRegistrarVenta()
            throws Exception {

        String body = """
        {
            "usuarioId":1,
            "fecha":"2026-06-13",
            "detalleIds":[]
        }
        """;

        mockMvc.perform(
                post("/api/ventas")
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(body)
        )
        .andExpect(
                status()
                        .isOk()
        );

    }

    @Test
    @WithMockUser(
            username = "usuario",
            roles = {
                    "USER"
            }
    )
    void usuarioSinRolNoDebeRegistrarVenta()
            throws Exception {

        String body = """
        {
            "usuarioId":1,
            "fecha":"2026-06-13",
            "detalleIds":[]
        }
        """;

        mockMvc.perform(
                post("/api/ventas")
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(body)
        )
        .andExpect(
                status()
                        .isForbidden()
        );

    }

}
