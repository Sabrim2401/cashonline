package ar.com.cashonline.cashonline.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ar.com.cashonline.cashonline.commom.PaginationResultRetrieved;
import ar.com.cashonline.cashonline.entities.Loan;
import ar.com.cashonline.cashonline.models.request.CrearLoans;
import ar.com.cashonline.cashonline.models.response.GenericResponse;
import ar.com.cashonline.cashonline.models.response.PaginationResponse;
import ar.com.cashonline.cashonline.service.LoansService;
import ar.com.cashonline.cashonline.service.UsuarioService;

@RestController
public class LoansController {

    private static final String LOANS_PATH = "/pageloans";

    @Autowired
    LoansService loansService;

    @Autowired
    UsuarioService userService;

    @Autowired
    PaginationResultRetrieved resultsRetrieved;

    @PostMapping("/loans")
    public ResponseEntity<GenericResponse> crearLoan(@RequestBody CrearLoans loan, Principal principal) {

        GenericResponse respuesta = new GenericResponse();

        if (loansService.validarAmount(loan.totalAmountLoan) == true) {
            Loan newLoan = loansService.crearLoan(loan, principal.getName());

            respuesta.message = "El prestamo ha sido creado correctamente.";
            respuesta.isOk = true;
            respuesta.id = newLoan.getLoanId();

            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.message = "El valor del prestamo debe ser mayor a cero";
            respuesta.isOk = false;
            return ResponseEntity.badRequest().body(respuesta);
        }
    }


    @GetMapping("/pageloans")
    public ResponseEntity<PaginationResponse> list(Pageable pageable,
            UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        PaginationResponse pResponse = loansService.findAll(pageable);

        resultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(uriBuilder,
                response, LOANS_PATH,
                pResponse.page,
                pResponse.totalPages,
                pResponse.size);

        return ResponseEntity.ok(pResponse);
    }

    @GetMapping("/loans-pageable")
    @ResponseBody
    public ResponseEntity<PaginationResponse> obtainLoans(Pageable pageable,
            UriComponentsBuilder uriBuilder, HttpServletResponse response,
            @RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) Integer userId) {

        if (userId != null) {

            var loansPages = loansService.findByUsuarioId(pageable, userId);

            return ResponseEntity.ok(loansPages);
        } else {

            PaginationResponse pResponse = loansService.findAll(pageable);

            resultsRetrieved.addLinkHeaderOnPagedResourceRetrieval(uriBuilder,
                    response, LOANS_PATH,
                    pResponse.page = page,
                    pResponse.totalPages,
                    pResponse.size = size);

            return ResponseEntity.ok().body(pResponse);

        }
    }

}
