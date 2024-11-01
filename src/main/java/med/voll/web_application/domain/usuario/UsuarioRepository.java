package med.voll.web_application.domain.usuario;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, BigDecimal> {
	
	Optional<Usuario> findByEmailIgnoreCase(String email);
  
	
	Optional<Usuario> findByTokenIgnoreCase(String Token);
}
