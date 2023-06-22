package com.fullcycle.admin.catalogo.domain.validation.handler;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class NotificationHandler implements ValidationHandler {
    private final List<Error> errors;

    private NotificationHandler(final List<Error> errors) {
        this.errors = errors;
    }

    public static NotificationHandler create() {
        return new NotificationHandler(new ArrayList<>());
    }

    public static NotificationHandler create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static NotificationHandler create(final Error anError) {
        return new NotificationHandler(new ArrayList<>()).append(anError);
    }

    @Override
    public NotificationHandler append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public NotificationHandler append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public NotificationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable ex) {
            this.errors.add(new Error(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
