package med.voll.web_application.domain.usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import med.voll.web_application.domain.RegraDeNegocioException;
import med.voll.web_application.domain.usuario.email.EmailService;



@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder encriptador;
	@Autowired
	private EmailService emailService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return usuarioRepository.findByEmailIgnoreCase(username).orElseThrow( () -> new UsernameNotFoundException("O usuario não foi encontrado"));
	}
	
	
	public BigDecimal salvarUsuario(String nome, String email, Perfil perfil) {
		
		String primeiraSenha = UUID.randomUUID().toString().substring(0, 8);
		  System.out.println("Senha gerada: " + primeiraSenha);
		
		//String senhaCriptografada = encriptador.encode(senha);
		  String senhaCriptografada = encriptador.encode(primeiraSenha);
		
		Usuario usuario = usuarioRepository.save(new Usuario(nome, email, senhaCriptografada,perfil));
		
		return usuario.getId();
	}


	public void excluir(BigDecimal id) {
		usuarioRepository.deleteById(id);
		
	}

	public void alterarSenha(DadosDeAlteracaoSenha dados, Usuario logado){
        if(!encriptador.matches(dados.senhaAtual(), logado.getPassword())){
            throw new RegraDeNegocioException("Senha digitada não confere com senha atual!");
        }

        if(!dados.novaSenha().equals(dados.novaSenhaConfirmacao())){
            throw new RegraDeNegocioException("Senha e confirmação não conferem!");
        }

        String senhaCriptografada = encriptador.encode(dados.novaSenha());
        logado.alterarSenha(senhaCriptografada);

        logado.setSenhaAlterada("S");

        usuarioRepository.save(logado);
    }

	
	public void enviarToken(String email) {
		Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
		
		String token = UUID.randomUUID().toString();
		usuario.setToken(token);
		usuario.setExpiracaoToken(LocalDateTime.now().plusMinutes(15));
		
		usuarioRepository.save(usuario);
		
		emailService.enviarEmailSenha(usuario);
	}


	public void recuperarConta(String codigo, DadosRecuperacaoConta dados) {
	
		Usuario usuario = usuarioRepository.findByTokenIgnoreCase(codigo)
				                           .orElseThrow( () -> new RegraDeNegocioException("Link Invalido!") ) ;
		
		
		 if (usuario.getExpiracaoToken().isBefore(LocalDateTime.now())) {
			 throw new RegraDeNegocioException("Link expirado!");
		 }
		 
		 if(!dados.novaSenha().equals(dados.novaSenhaConfirmacao())){
	            throw new RegraDeNegocioException("Senha e confirmação não conferem!");
	        }
		
		 
		 String senhaCriptografada = encriptador.encode(dados.novaSenha());
	        usuario.alterarSenha(senhaCriptografada);
	        
	        usuario.setToken(null);
	        usuario.setExpiracaoToken(null);
	        
	        usuarioRepository.save(usuario);
		
	}
	
	
	/*private static void atualizarUsuarioSpringSecurity(Usuario logado) {
	    var authentication = SecurityContextHolder.getContext().getAuthentication();
	    var newAuth = new UsernamePasswordAuthenticationToken(logado, null, authentication.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(newAuth);
	}*/
/*
     // Força o logout do usuário
    SecurityContextHolder.clearContext(); // Limpa o contexto de segurança

    // Invalida a sessão atual, deslogando o usuário
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attr.getRequest();
    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate(); // Invalida a sessão
    }
*/
	
}
