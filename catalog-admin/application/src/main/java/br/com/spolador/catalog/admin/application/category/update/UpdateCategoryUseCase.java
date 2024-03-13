package br.com.spolador.catalog.admin.application.category.update;

import br.com.spolador.catalog.admin.application.UseCase;
import br.com.spolador.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
