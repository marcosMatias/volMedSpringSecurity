package med.voll.web_application.domain.medico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, BigDecimal> {

    @Query("""
            SELECT
                CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END
            FROM
                Medico m
            WHERE (m.email = :email OR m.crm = :crm) AND (:id IS NULL OR m.id <> :id)
            """)
    boolean isJaCadastrado(String email, String crm, BigDecimal id);

    List<Medico> findByEspecialidade(Especialidade especialidade);

}
