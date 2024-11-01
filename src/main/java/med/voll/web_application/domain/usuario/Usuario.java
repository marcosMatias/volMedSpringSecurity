package med.voll.web_application.domain.usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_s")
    @SequenceGenerator(sequenceName = "usuarios_s", allocationSize = 1, name = "usuarios_s")
    private BigDecimal id;
    private String nome;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    
    private String senhaAlterada;
    
    private String token;
    
    private LocalDateTime expiracaoToken;
 
    public Usuario() {
		
	}

	public Usuario(String nome, String email, String senha,Perfil perfil) {
		
		this.nome   = nome;
		this.email  = email;
		this.senha  = senha;
		this.perfil = perfil;
		this.senhaAlterada = "N";
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+perfil.name()));
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
    
    public Perfil getPerfil() {
    	return perfil;
    }
    
    
    public void alterarSenha(String senhaCriptografada) {
    	this.senha = senhaCriptografada;
    }
    
    
    public String getSenhaAlterada() {
        return senhaAlterada;
    }

    public void setSenhaAlterada(String senhaAlterada) {
        this.senhaAlterada = senhaAlterada;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiracaoToken() {
		return expiracaoToken;
	}

	public void setExpiracaoToken(LocalDateTime expiracaoToken) {
		this.expiracaoToken = expiracaoToken;
	}
    
    
    
    
}
