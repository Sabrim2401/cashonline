package ar.com.cashonline.cashonline.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

import ar.com.cashonline.cashonline.entities.Usuario;
import ar.com.cashonline.cashonline.models.request.*;
import ar.com.cashonline.cashonline.models.response.*;

import ar.com.cashonline.cashonline.security.jwt.JWTTokenUtil;
import ar.com.cashonline.cashonline.service.JWTUserDetailsService;
import ar.com.cashonline.cashonline.service.UsuarioService;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;

@RestController
public class AuthController {
    @Autowired
    UsuarioService usuarioService;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    // Auth : authentication ->
    @PostMapping("auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {
                
        RegistrationResponse r = new RegistrationResponse();
    
        Usuario usuario = usuarioService.crearUsuario(req.email,req.firstname,req.lastname,req.username,req.password);
        
        r.isOk = true;
        r.message = "Te registraste con exito!";
        r.userId = usuario.getUserId(); // <-- AQUI ponemos el numerito de id para darle a front!

        return ResponseEntity.ok(r);

    }

    @PostMapping("auth/login") // probando nuestro login
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        Map<String, Object> claims = usuarioService.getUserClaims(usuarioLogueado);

       
        // ver esos ids de que user pertenecen si logran interceptar el token
        // Por eso es que en cada request debemos validar el token(firma)
        String token = jwtTokenUtil.generateToken(userDetails, claims);

        Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUserId();
     // r.userType = u.g();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);
    }
}
