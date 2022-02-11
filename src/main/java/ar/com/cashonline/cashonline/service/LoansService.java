package ar.com.cashonline.cashonline.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import ar.com.cashonline.cashonline.entities.Loan;
import ar.com.cashonline.cashonline.models.request.CrearLoans;
import ar.com.cashonline.cashonline.models.response.LoansResponse;
import ar.com.cashonline.cashonline.models.response.PaginationResponse;
import ar.com.cashonline.cashonline.repos.LoansRepository;

@Service
public class LoansService {

    @Autowired
    LoansRepository repo;

    @Autowired
    UsuarioService serviceUser;

    public void crearLoan(Loan loan) {
        repo.save(loan);
    }

    public List<Loan> traerLoans() {
        return repo.findAll();
    }

    public Loan buscarPorId(Integer id) {
        return repo.findByLoanId(id);
    }

    public List<LoansResponse> findAllLoans() {

        List<Loan> loans = repo.findAll();
        List<LoansResponse> loansRe = new ArrayList<>(loans.size());

        for (Loan loan : loans) {

            LoansResponse loansResponse = new LoansResponse();

            loansResponse.loanId = loan.getLoanId();
            loansResponse.total = loan.getTotalAmountLoan();
            loansResponse.userId = loan.getUsuario().getUserId();
            loansRe.add(loansResponse);
        }
        return loansRe;
    }

    public Loan crearLoan(CrearLoans loan, String username) {

        var user = serviceUser.buscarPorUsername(username);
        Loan newLoan = new Loan();
        newLoan.setTotalAmountLoan(loan.totalAmountLoan);
        newLoan.setUsuario(user);
        return repo.save(newLoan);

    }

    public PaginationResponse findAll(Pageable pageable) {
        Page<Loan> page = repo.findAll(pageable);
        List<LoansResponse> loanResponse = buildList(page.getContent());
        return buildPaginationResponse(loanResponse, page);
    }

    public PaginationResponse findByUsuarioId(Pageable pageable, Integer userId) {

        Page<Loan> page = repo.findAll(pageable);

        List<LoansResponse> loanResponse = buildList(page.getContent());

        List<LoansResponse> loansByUser = new ArrayList<>();

        for (LoansResponse loansResponse : loanResponse) {

            if (loansResponse.userId == userId) {
                loansByUser.add(loansResponse);
            }

        }

        return buildPaginationResponse(loansByUser, page);
    }

    private PaginationResponse buildPaginationResponse(List<LoansResponse> loansResponse,
            Page<Loan> page) {
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.loansResponse = loansResponse;
        paginationResponse.page = page.getNumber();
        paginationResponse.totalPages = page.getTotalPages();
        paginationResponse.size = page.getSize();
        return paginationResponse;
    }

    public List<LoansResponse> buildList(List<Loan> loans) {

        List<LoansResponse> loanR = new ArrayList<>();
        for (Loan loan : loans) {

            LoansResponse lResponse = new LoansResponse();

            lResponse.total = loan.getTotalAmountLoan();
            lResponse.userId = loan.getUsuario().getUserId();
            lResponse.loanId = loan.getLoanId();

            loanR.add(lResponse);

        }

        return loanR;
    }

    public boolean validarAmount(Integer amount) {

        if (amount < 0) 
            return false;
        return true;
    }



}
