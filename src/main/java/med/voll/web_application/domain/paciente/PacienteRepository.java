package med.voll.web_application.domain.paciente;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, BigDecimal> {
    @Query("""
            SELECT
                CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
            FROM
                Paciente p
            WHERE (p.email = :email OR p.cpf = :cpf) AND (:id IS NULL OR p.id <> :id)
            """)
    boolean isJaCadastrado(String email, String cpf, BigDecimal id);

    Optional<Paciente> findByCpf(String cpf);

}
