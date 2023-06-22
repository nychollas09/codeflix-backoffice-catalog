package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.application.UseCase;
import com.fullcycle.admin.catalogo.domain.validation.handler.NotificationHandler;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
        extends UseCase<
            CreateCategoryCommand,
            Either<NotificationHandler, CreateCategoryOutput>
        > {
}
