package br.com.spolador.catalog.admin.application.category.retrieve.get;

import br.com.spolador.catalog.admin.domain.category.CategoryGateway;
import br.com.spolador.catalog.admin.domain.category.CategoryID;
import br.com.spolador.catalog.admin.domain.exceptions.DomainException;
import br.com.spolador.catalog.admin.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(final String anId) {
        final var anCategoryId = CategoryID.from(anId);

        return this.categoryGateway.findById(anCategoryId)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryId));
    }

    private Supplier<DomainException> notFound(final CategoryID anId){
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue()))
        );
    }
}
