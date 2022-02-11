package ar.com.cashonline.cashonline.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


public class GenericResponse {
    public boolean isOk;
    public String message;
    
    @JsonInclude(Include.NON_NULL)
    public Integer id;
}
