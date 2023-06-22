package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {
    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default boolean hasErrors() {
        return this.getErrors() != null && !this.getErrors().isEmpty();
    }

    default Error firstError() {
        return this.hasErrors() ? this.getErrors().get(0) : null;
    }

    public interface Validation {
        void validate();
    }
}
