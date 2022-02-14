package ar.com.cashonline.cashonline.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.BadCredentialsException;

import ar.com.cashonline.cashonline.entities.Usuario;
import ar.com.cashonline.cashonline.repos.UsuarioRepository;
import ar.com.cashonline.cashonline.security.Crypto;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    public Usuario crearUsuario(String email, String firstname, String lastname, String username, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setFirstname(firstname);
        usuario.setLastname(lastname);
        usuario.setUsername(username);
        usuario.setPassword(Crypto.encrypt(password, username));
        return repo.save(usuario);
    }

    public Usuario buscarPorUsername(String username) {
        return repo.findByUsername(username);
    }

    public Usuario findById(Integer id) {
        return repo.findByUserId(id);
    }

    public UserDetails getUserAsUserDetail(Usuario usuario) {
        UserDetails uDetails;

        uDetails = new User(usuario.getUsername(), usuario.getPassword(), getAuthorities(usuario));

        return uDetails;
    }

    Set<? extends GrantedAuthority> getAuthorities(Usuario usuario) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        Integer userType = usuario.getUserId();

        authorities.add(new SimpleGrantedAuthority("CLAIM_userType_" + userType.toString()));

        return authorities;
    }

    public Usuario login(String username, String password) {

        Usuario u = buscarPorUsername(username);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getUsername()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

        return u;
    }

    public Map<String, Object> getUserClaims(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userType", usuario.getUserId()); ///

        return claims;
    }

    public void bajaUsuarioPorId(Integer id) {
        repo.deleteById(id);
    }

    public boolean validarEmail(String email) {

        if (repo.findByEmail(email) != null)
            return false;
        return true;
    }
}
