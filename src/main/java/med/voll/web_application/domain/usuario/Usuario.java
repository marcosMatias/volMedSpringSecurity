package med.voll.web_application.domain.usuario;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_s")
    @SequenceGenerator(sequenceName = "usuarios_s", allocationSize = 1, name = "usuarios_s")
    private BigDecimal id;
    private String nome;
    private String email;
    private String senha;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    
    public String getNome() {
    	return nome;
    }
    
    public BigDecimal getId() {
    	return this.id;
    }
}
