package br.com.spolador.catalog.admin.application.category.retrieve.list;

import br.com.spolador.catalog.admin.application.UseCase;
import br.com.spolador.catalog.admin.domain.category.CategorySearchQuery;
import br.com.spolador.catalog.admin.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
