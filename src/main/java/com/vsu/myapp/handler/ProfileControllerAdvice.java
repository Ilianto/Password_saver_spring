package com.vsu.myapp.handler;

import com.vsu.myapp.exception.CryptException;
import com.vsu.myapp.exception.NotFoundException;
import com.vsu.myapp.exception.RepositoryException;
import com.vsu.myapp.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ProfileControllerAdvice {
    private final static Logger LOGGER =  Logger.getLogger(ProfileControllerAdvice.class.getName());

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(NotFoundException e){
        LOGGER.log(Level.WARNING,"not found exception",e);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleValidation(NotFoundException e){
        LOGGER.log(Level.WARNING,"validation exception",e);
    }

    @ExceptionHandler(CryptException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleCryptException(CryptException e){LOGGER.log(Level.WARNING,"crypt exception",e);}

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleRepositoryException(RepositoryException e){LOGGER.log(Level.WARNING,"repository exception",e);}
}
