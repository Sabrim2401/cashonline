package ar.com.cashonline.cashonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.cashonline.cashonline.entities.Usuario;
import ar.com.cashonline.cashonline.models.response.GenericResponse;
import ar.com.cashonline.cashonline.models.response.UserResponse;
import ar.com.cashonline.cashonline.service.UsuarioService;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioService uService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UserResponse> getUsuario(@PathVariable Integer id){
        var user = uService.findById(id);
        UserResponse uResponse = new UserResponse();
        uResponse.id = user.getUserId();
        uResponse.email = user.getEmail();
        uResponse.firstname = user.getFirstname();
        uResponse.lastname = user.getLastname();
        uResponse.loans = user.getLoans();

        return ResponseEntity.ok(uResponse);
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> bajaUsuario(@PathVariable Integer id){

        uService.bajaUsuarioPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "El usuario fue dado de baja con exito";

        return ResponseEntity.ok(respuesta);

    }

}


