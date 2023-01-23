package pe.parnertdigital.testcontrol5.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pe.parnertdigital.testcontrol5.models.Cuenta;
import pe.parnertdigital.testcontrol5.models.TransaccionDto;
import pe.parnertdigital.testcontrol5.services.CuentaService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta detalle(@PathVariable Long id){
        return cuentaService.buscarPorId(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto){
        cuentaService.tranferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(), dto.getMonto(), dto.getBancoId());
        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status","OK");
        response.put("mensaje", "Transferencia realizada con exito!");
        response.put("transaccion", dto);
        return ResponseEntity.ok(response);
    }
}
