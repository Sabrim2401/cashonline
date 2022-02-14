package ar.com.cashonline.cashonline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ar.com.cashonline.cashonline.service.LoansService;
import ar.com.cashonline.cashonline.service.UsuarioService;
import ar.com.cashonline.cashonline.entities.Loan;
import ar.com.cashonline.cashonline.repos.UsuarioRepository;

@SpringBootTest
class CashonlineApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	LoansService service;

	@Autowired
	UsuarioService userService;

	@MockBean
	UsuarioRepository repo;

	@Test
	void loanTestAmountOk() {

		Loan loanAmountOK = new Loan();
		loanAmountOK.setTotalAmountLoan(100);

		assertTrue(service.validarAmount(loanAmountOK.getTotalAmountLoan()));

	}


}
