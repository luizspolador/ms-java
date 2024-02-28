package br.com.spolador.catalog.admin.application.category.create;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {

    public static CreateCategoryOutput from (final Category aCategory){
        return new CreateCategoryOutput(aCategory.getId());
    }
}
