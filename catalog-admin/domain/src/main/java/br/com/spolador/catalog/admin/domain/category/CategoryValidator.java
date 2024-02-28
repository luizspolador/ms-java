package br.com.spolador.catalog.admin.domain.category;

import br.com.spolador.catalog.admin.domain.validation.Error;
import br.com.spolador.catalog.admin.domain.validation.ValidationHandler;
import br.com.spolador.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category aCategory, ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        if(this.category.getName() == null){
            this.validationHandler().append(new Error("'name' should be not null"));
        }
    }
}
