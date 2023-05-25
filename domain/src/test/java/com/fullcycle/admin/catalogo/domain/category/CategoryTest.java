package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedIsActive);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "Fi ";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of
                classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin
                professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words,
                consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical
                literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33
                of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC.
                This book is a treatise on the theory of ethics, very popular during the Renaissance. The first
                line of Lorem Ipsum, "Lorem ipsum dolor sit amet..", comes from a line in section 1.10.32. The
                standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.
                Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero are also reproduced
                in their exact original form, accompanied by English versions from the 1914 translation by
                H. Rackham.
                """;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characteres";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler())
        );

        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final var expectedName = "Filmes";
        final var expectedDescription = "  ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedIsActive);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(actualCategory.getName(), expectedName);
        Assertions.assertEquals(actualCategory.getDescription(), expectedDescription);
        Assertions.assertEquals(actualCategory.isActive(), expectedIsActive);
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }
}
