package ar.com.cashonline.cashonline.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.cashonline.cashonline.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String userName);

    Usuario findByEmail(String email);

    Usuario findByUserId(Integer id);

}
