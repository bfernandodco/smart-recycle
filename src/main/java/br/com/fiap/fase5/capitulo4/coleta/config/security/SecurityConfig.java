package br.com.fiap.fase5.capitulo4.coleta.config.security;

import br.com.fiap.fase5.capitulo4.coleta.auth.VerificarToken;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = SecurityConfig.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfig {
    public static final String SECURITY = "bearerAuth";
    private static final String[] AUTH_PATH = {
            "/v3/api-docs/**",
            "/swagger-ui.html/**",
            "/v2/api/docs/**",
            "/swagger-resources/**"
    };

    @Autowired
    private VerificarToken verificarToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v2/coleta/usuario/cadastro").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v2/coleta/usuario/atualizar-dados").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/coleta/pontos-de-coleta/listar/").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/coleta/agenda/coletas-agendadas/").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/coleta/caminhoes/buscar/").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/coleta/caminhoes/listar").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/v2/coleta/rotas/listar/").hasRole("USER")
                    .anyRequest().hasRole("ADMIN"))
                .addFilterBefore(verificarToken, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

}
