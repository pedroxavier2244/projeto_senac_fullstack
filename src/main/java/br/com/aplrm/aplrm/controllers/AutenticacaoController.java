package br.com.aplrm.aplrm.controllers;

import br.com.aplrm.aplrm.dto.UserPerfilDTO;
import br.com.aplrm.aplrm.entities.User;
import br.com.aplrm.aplrm.entities.usuario.DadosAutenticacao;
import br.com.aplrm.aplrm.security.DadosTokenJWT;
import br.com.aplrm.aplrm.services.TokenServices;
import br.com.aplrm.aplrm.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenServices tokenServices;

    @Autowired
    private UserService userService;

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Object>> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        try {
            if (dados.email() == null || dados.senha() == null) {
                throw new IllegalArgumentException("Email or password cannot be null");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dados.email(), dados.senha()
            );
            var authentication = authenticationManager.authenticate(authenticationToken);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

            String token = tokenServices.gerarToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);

            response.put("user", new UserPerfilDTO(user));

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid input: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/autenticacao/validarToken")
    public ResponseEntity<String> validarToken(HttpServletRequest request) {
        System.out.println("testando autenticacao");
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            boolean isValid = tokenServices.isValid(token.substring(7));
            return isValid ? ResponseEntity.ok("Token válido") : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido");
        }
        return ResponseEntity.badRequest().body("Token não fornecido");
    }


    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> efetuarLoginAdmin(@RequestBody @Valid DadosAutenticacao dados) {
        try {
            if (dados.email() == null || dados.senha() == null) {
                throw new IllegalArgumentException("Email or password cannot be null");
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dados.email(), dados.senha()
            );

            var authentication = authenticationManager.authenticate(authenticationToken);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = (User) userDetailsService.loadUserByUsername(userDetails.getUsername());

            boolean verificar = user.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("CLIENT"));

            if (verificar) {
                throw new RuntimeException("Usuário não autorizado");
            }

            String token = tokenServices.gerarToken(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("user", new UserPerfilDTO(user));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid input: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


//   @PostMapping
//   public ResponseEntity<String> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
//       var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
//       var authentication = authenticationManager.authenticate(authenticationToken);
//       UserDetails user = (UserDetails) authentication.getPrincipal();
//       String token = tokenServices.gerarToken((User) user);
//       return ResponseEntity.ok(new DadosTokenJWT(token));
//   }
}

