package pe.parnertdigital.testcontrol5.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pe.parnertdigital.testcontrol5.Datos;
import pe.parnertdigital.testcontrol5.services.CuentaService;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static  pe.parnertdigital.testcontrol5.Datos.*;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CuentaService cuentaService;

    @Test
    void testDetalle() throws Exception {
//        when(cuentaService.buscarPorId(1l)).thenReturn(Datos.crearCuenta001().orElseThrow(NoSuchElementException::new));
        when(cuentaService.buscarPorId(1l)).thenReturn(crearCuenta001().orElseThrow(NoSuchElementException::new));//esta de forma estatica, es lo mismo de arriba
        //When
//        mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON)) //esta de forma estatica, es lo mismo de arriba
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("David"))
                .andExpect(jsonPath("$.saldo").value("1000"));
    }
}