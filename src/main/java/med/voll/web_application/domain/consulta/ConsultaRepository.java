package med.voll.web_application.domain.consulta;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultaRepository extends JpaRepository<Consulta, BigDecimal> {

    Page<Consulta> findAllByOrderByData(Pageable paginacao);
    
    @Query("SELECT c FROM Consulta c " +
            "WHERE (c.medico.id = :id OR c.paciente.id = :id)" +
            " ORDER BY c.data")
    Page<Consulta> buscarPersonalizadaConsultas(BigDecimal id,   Pageable paginacao );

}
