package ar.com.cashonline.cashonline.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.cashonline.cashonline.entities.Loan;

@Repository
public interface LoansRepository extends JpaRepository<Loan, Integer> {

    Loan findByLoanId(Integer id);

    Loan findByUsuario(Integer id);

    Page<Loan> findAll(Pageable pageable);


}
