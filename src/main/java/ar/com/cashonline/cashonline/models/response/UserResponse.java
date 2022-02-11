package ar.com.cashonline.cashonline.models.response;

import java.util.List;

import ar.com.cashonline.cashonline.entities.Loan;

public class UserResponse {

  public Integer id;
  public String email;
  public String firstname;
  public String lastname;
  public List<Loan> loans;

}
