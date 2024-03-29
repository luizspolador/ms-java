package br.com.spolador.catalog.admin.domain.category;

import br.com.spolador.catalog.admin.domain.validation.Error;
import br.com.spolador.catalog.admin.domain.validation.ValidationHandler;
import br.com.spolador.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 3;

    public CategoryValidator(final Category aCategory, ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();
        if(name == null){
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final int length = name.trim().length(); // trim() para tirar os espaços vazios e validar o número de caracteres
        if(length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH){
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
