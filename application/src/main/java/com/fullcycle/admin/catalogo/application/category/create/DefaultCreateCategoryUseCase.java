package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.validation.handler.NotificationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway anCategoryGateway){
        this.categoryGateway = Objects.requireNonNull(anCategoryGateway);
    }

    @Override
    public Either<NotificationHandler, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var notification = NotificationHandler.create();

        final var aCategory = Category.newCategory(
                aCommand.name(),
                aCommand.description(),
                aCommand.isActive()
        );
        aCategory.validate(notification);

        return notification.hasErrors() ? API.Left(notification) : create(aCategory);
    }

    private Either<NotificationHandler, CreateCategoryOutput> create(Category aCategory) {
        return API.Try(
                () -> this.categoryGateway.create(aCategory)
        ).toEither().bimap(
                NotificationHandler::create, 
                CreateCategoryOutput::from
        );
    }
}
