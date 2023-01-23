package pe.parnertdigital.testcontrol5;


import pe.parnertdigital.testcontrol5.models.Banco;
import pe.parnertdigital.testcontrol5.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {

    public  static Optional<Cuenta> crearCuenta001(){
        return  Optional.of(new Cuenta(1L, "David", new BigDecimal("1000")));
    }
    public  static Optional<Cuenta>  crearCuenta002(){
        return  Optional.of(new Cuenta(2L, "Ezer", new BigDecimal("2000")));
    }
    public static Optional<Banco>  crearBanco(){
        return  Optional.of(new Banco(1L,"El banco financiero", 0));
    }

}
