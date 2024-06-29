package com.development.login_crud.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;
@Data
public class ApiErrorMessage {

    private HttpStatusCode httpStatus;

    private List<String> errors;

    public ApiErrorMessage(HttpStatusCode httpStatus, List<String> errors) {
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ApiErrorMessage(HttpStatusCode status, String error) {
        super();
        this.httpStatus = status;
        errors = Collections.singletonList(error);
    }
}
