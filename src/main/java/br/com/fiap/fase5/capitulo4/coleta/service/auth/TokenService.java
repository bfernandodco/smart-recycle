package br.com.fiap.fase5.capitulo4.coleta.service.auth;

import br.com.fiap.fase5.capitulo4.coleta.dto.LoginDto;
import br.com.fiap.fase5.capitulo4.coleta.model.Usuario;
import br.com.fiap.fase5.capitulo4.coleta.repository.UsuarioRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    public String gerarToken(Usuario usuario) {
        try {
            return JWT.create()
                    .withIssuer("SmartRecicle").withSubject(usuario.getCpf())
                    .withExpiresAt(expiracao())
                    .sign(algoritmo());
        } catch(JWTCreationException e) {
            throw new RuntimeException("Não foi possível gerar o Token.");
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algoritmo())
                    .withIssuer("SmartRecicle")
                    .build().verify(token).getSubject();
        } catch(JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado.");
        }
    }

    public Instant expiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public Algorithm algoritmo() {
        return Algorithm.HMAC256(jwtSecret);
    }

}
