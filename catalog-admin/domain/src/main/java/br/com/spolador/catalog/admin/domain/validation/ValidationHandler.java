package br.com.spolador.catalog.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default boolean hasErrors(){
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError(){
        if (getErrors() != null && !getErrors().isEmpty()){
            return getErrors().get(0);
        } else {
            return null;
        }
    }


    //possibilita passar um método como uma lambda function que lança uma exceção. Encapusalando o comportamento.
    public interface Validation{
        void validate();
    }
}
