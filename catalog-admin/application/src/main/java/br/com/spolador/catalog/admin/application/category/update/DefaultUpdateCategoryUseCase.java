package br.com.spolador.catalog.admin.application.category.update;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryGateway;
import br.com.spolador.catalog.admin.domain.category.CategoryID;
import br.com.spolador.catalog.admin.domain.exceptions.DomainException;
import br.com.spolador.catalog.admin.domain.validation.Error;
import br.com.spolador.catalog.admin.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.desciption();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        aCategory.update(aName, aDescription, isActive).validate(notification);

        return notification.hasErrors() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID anId){
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue()))
        );
    }
}
