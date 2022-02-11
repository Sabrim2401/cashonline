package ar.com.cashonline.cashonline.models.response;

import java.util.List;

public class PaginationResponse {
    
    public List<LoansResponse> loansResponse;
    public int page;
    public int totalPages;
    public int size;

}
