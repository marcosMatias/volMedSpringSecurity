package med.voll.web_application.domain.consulta;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultaRepository extends JpaRepository<Consulta, BigDecimal> {

    Page<Consulta> findAllByOrderByData(Pageable paginacao);

}
