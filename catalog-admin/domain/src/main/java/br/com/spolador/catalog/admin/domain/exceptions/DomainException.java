package br.com.spolador.catalog.admin.domain.exceptions;

import br.com.spolador.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException{

    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> AnErrors){
        super(aMessage);
        this.errors = AnErrors;
    }

    public static DomainException with(final Error anError){
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> AnErrors){
        return new DomainException("", AnErrors);
    }


    public List<Error> getErrors() {
        return errors;
    }
}
