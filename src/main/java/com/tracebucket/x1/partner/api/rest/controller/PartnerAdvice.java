package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.x1.partner.api.rest.exception.PartnerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sadath on 26-May-2015.
 */
@ControllerAdvice
public class PartnerAdvice {
    @ExceptionHandler(PartnerException.class)
    @ResponseBody
    public ResponseEntity<String> handleOrganizationException(PartnerException ex) {
        return new ResponseEntity<String>(ex.getMessage(), ex.getHttpStatus());
    }
}