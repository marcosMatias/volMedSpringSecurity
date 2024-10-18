package med.voll.web_application.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Value("${vollmed.rememberme}")
	private String rememberMeKey;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http.authorizeHttpRequests(req -> {
            									req.requestMatchers("/css/**", "/js/**", "/assets/**","/","/index","/home").permitAll();
            									req.anyRequest().authenticated();
            									})
					.formLogin(form -> form.loginPage("/login")
									       .defaultSuccessUrl("/")
									       .permitAll())
					.logout(logout -> logout.logoutSuccessUrl("/login?logout")
					.permitAll())
					.rememberMe(rememberMe -> rememberMe.key(rememberMeKey)
														.tokenValiditySeconds(1800))
					.csrf(Customizer.withDefaults())
					.build();
	}
	
	
	
	
	@Bean
	public PasswordEncoder codificadorSenha() {
		
		return new BCryptPasswordEncoder();
	}
	
	
	
	
	
	
	

	
}
