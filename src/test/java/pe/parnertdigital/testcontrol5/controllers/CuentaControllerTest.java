package pe.parnertdigital.testcontrol5.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pe.parnertdigital.testcontrol5.Datos;
import pe.parnertdigital.testcontrol5.models.TransaccionDto;
import pe.parnertdigital.testcontrol5.services.CuentaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

    ObjectMapper objectMapper;

    @BeforeEach
    void serUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDetalle() throws Exception {
        //Given
//        when(cuentaService.buscarPorId(1l)).thenReturn(Datos.crearCuenta001().orElseThrow(NoSuchElementException::new));
        when(cuentaService.buscarPorId(1l)).thenReturn(crearCuenta001().orElseThrow(NoSuchElementException::new));//esta de forma estatica, es lo mismo de arriba
        //When
//        mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON)) //esta de forma estatica, es lo mismo de arriba
        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("David"))
                .andExpect(jsonPath("$.saldo").value("1000"));
        verify(cuentaService).buscarPorId(1L);
    }

    @Test
    void testTransferir() throws Exception, JsonProcessingException {
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        //when
        mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito!"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(1L));
    }
}