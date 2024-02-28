package br.com.spolador.catalog.admin.domain.exceptions;

import br.com.spolador.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainException extends RuntimeException{

    private final List<Error> errors;

    private DomainException(final List<Error> AnErrors){
        super("", null, true, false);
        this.errors = AnErrors;
    }

    public static DomainException with(final List<Error> AnErrors){
        return new DomainException(AnErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
