package ar.com.cashonline.cashonline.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @Column(name = "loan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @Column(name = "total_amount_loan")
    private Integer totalAmountLoan;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id") 
    @JsonIgnore
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getTotalAmountLoan() {
        return totalAmountLoan;
    }

    public void setTotalAmountLoan(Integer totalAmountLoan) {
        this.totalAmountLoan = totalAmountLoan;
    }

}
