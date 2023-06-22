package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
        Mockito.verify(
                categoryGateway,
                Mockito.times(1)
        ).create(
                Mockito.argThat(
                        aCategory -> {
                            return Objects.nonNull(aCategory.getId()) &&
                                    Objects.equals(expectedName, aCategory.getName()) &&
                                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                                    Objects.equals(expectedIsActive, aCategory.isActive()) &&
                                    Objects.nonNull(aCategory.getCreatedAt()) &&
                                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                                    Objects.isNull(aCategory.getDeletedAt());
                        }
                )
        );
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "A melhor categoria";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        final var actualException = Assertions.assertThrows(
                DomainException.class,
                () -> this.useCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Mockito.verify(
                this.categoryGateway,
                Mockito.times(0)
        ).create(Mockito.any());
    }

    @Test
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(this.categoryGateway.create(Mockito.any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = this.useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
        Mockito.verify(
                this.categoryGateway,
                Mockito.times(1)
        ).create(
                Mockito.argThat(
                        aCategory -> {
                            return Objects.nonNull(aCategory.getId()) &&
                                    Objects.equals(expectedName, aCategory.getName()) &&
                                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                                    Objects.equals(expectedIsActive, aCategory.isActive()) &&
                                    Objects.nonNull(aCategory.getCreatedAt()) &&
                                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                                    Objects.nonNull(aCategory.getDeletedAt());
                        }
                )
        );
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A melhor categoria";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway Error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(this.categoryGateway.create(Mockito.any()))
                .thenThrow(new IllegalStateException("Gateway Error"));

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> this.useCase.execute(aCommand)
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
        Mockito.verify(
                this.categoryGateway,
                Mockito.times(expectedErrorCount)
        ).create(
                Mockito.argThat(
                        aCategory -> {
                            return Objects.nonNull(aCategory.getId()) &&
                                    Objects.equals(expectedName, aCategory.getName()) &&
                                    Objects.equals(expectedDescription, aCategory.getDescription()) &&
                                    Objects.equals(expectedIsActive, aCategory.isActive()) &&
                                    Objects.nonNull(aCategory.getCreatedAt()) &&
                                    Objects.nonNull(aCategory.getUpdatedAt()) &&
                                    Objects.isNull(aCategory.getDeletedAt());
                        }
                )
        );
    }
}
