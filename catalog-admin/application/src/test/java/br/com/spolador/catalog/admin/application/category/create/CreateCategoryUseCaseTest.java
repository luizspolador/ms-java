package br.com.spolador.catalog.admin.application.category.create;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;

public class CreateCategoryUseCaseTest {

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(returnsFirstArg()); // qd o metodo create for chamado, retorna o primeiro parametro

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory -> {
                    return Objects.equals(expectedName, aCategory.getName())
                            && Objects.equals(expectedDescription, aCategory.getDescription())
                            && Objects.equals(expectedIsActive, aCategory.isActive())
                            && Objects.nonNull(aCategory.getId())
                            && Objects.nonNull(aCategory.getCreatedAt())
                            && Objects.nonNull(aCategory.getUpdatedAt())
                            && Objects.isNull(aCategory.getDeletedAt());
                }
        ));
    }
}
