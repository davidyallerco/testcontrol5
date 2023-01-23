package pe.parnertdigital.testcontrol5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pe.parnertdigital.testcontrol5.models.Cuenta;

import java.util.Optional;

public interface CuentaRespository extends JpaRepository<Cuenta, Long> {


    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta>  findByPersona(String persona);

}
