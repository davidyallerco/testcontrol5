package pe.parnertdigital.testcontrol5.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pe.parnertdigital.testcontrol5.models.Cuenta;
import pe.parnertdigital.testcontrol5.services.CuentaService;


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
}
