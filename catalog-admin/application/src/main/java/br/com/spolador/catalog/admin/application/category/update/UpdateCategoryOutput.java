package br.com.spolador.catalog.admin.application.category.update;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {

    public static UpdateCategoryOutput from (final Category aCategory){
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
