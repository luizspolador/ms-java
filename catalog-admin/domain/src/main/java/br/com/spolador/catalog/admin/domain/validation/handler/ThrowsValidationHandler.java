package br.com.spolador.catalog.admin.domain.validation.handler;

import br.com.spolador.catalog.admin.domain.exceptions.DomainException;
import br.com.spolador.catalog.admin.domain.validation.Error;
import br.com.spolador.catalog.admin.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    //Lança uma exceção sempre que tiver um erro, não é acumulativo.
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(List.of(anError));
    }

    @Override
    public ValidationHandler append(final ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final Exception e){
            throw DomainException.with(List.of(new Error(e.getMessage())));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
