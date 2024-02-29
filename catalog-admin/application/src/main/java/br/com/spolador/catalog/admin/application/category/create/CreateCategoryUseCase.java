package br.com.spolador.catalog.admin.application.category.create;

import br.com.spolador.catalog.admin.application.UseCase;
import br.com.spolador.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
