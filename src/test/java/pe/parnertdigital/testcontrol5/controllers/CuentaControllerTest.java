package pe.parnertdigital.testcontrol5.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pe.parnertdigital.testcontrol5.Datos;
import pe.parnertdigital.testcontrol5.models.Cuenta;
import pe.parnertdigital.testcontrol5.models.TransaccionDto;
import pe.parnertdigital.testcontrol5.services.CuentaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static  pe.parnertdigital.testcontrol5.Datos.*;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static  org.hamcrest.Matchers.*;

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

        System.out.println(objectMapper.writeValueAsString(dto));
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje", "Transferencia realizada con exito!");
        response.put("transaccion", dto);
        System.out.println(objectMapper.writeValueAsString(response));


        //when
        mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))

        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito!"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(dto.getCuentaOrigenId()))

                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testListar() throws Exception , JsonProcessingException{
        //Given
        List<Cuenta> cuentas = Arrays.asList(crearCuenta001().orElseThrow(IllegalStateException::new),crearCuenta002().orElseThrow(IllegalStateException::new));
        when(cuentaService.buscarTodos()).thenReturn(cuentas);
        //When
        mvc.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
        //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].persona").value("David"))
                .andExpect(jsonPath("$[1].persona").value("Ezer"))
                .andExpect(jsonPath("$[0].saldo").value("1000"))
                .andExpect(jsonPath("$[1].saldo").value("2000"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(cuentas)));

    }

    @Test
    @Disabled
    void testGuardar() throws Exception {
        //Given
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        when(cuentaService.guardar(any())).then(invocation ->{
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        //When
        mvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.persona", is("Pepe")))
                .andExpect(jsonPath("$.saldo", is("3000")));
        verify(cuentaService).guardar(any());
    }
}