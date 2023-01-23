package pe.parnertdigital.testcontrol5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.parnertdigital.testcontrol5.models.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long> {

}
