package br.com.spolador.catalog.admin.application.category.update;

import br.com.spolador.catalog.admin.application.category.create.CreateCategoryCommand;
import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryGateway;
import br.com.spolador.catalog.admin.domain.category.CategoryID;
import br.com.spolador.catalog.admin.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase usecase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCategory_ShouldReturnCategoryId(){
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        Mockito.when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = usecase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
                ));

    }

    @Test
    public void givenAnInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException(){
        final var aCategory = Category.newCategory("Film", null, true);
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));

        final var notification = usecase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_ShouldReturnInactivatedCategoryId(){
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        Mockito.when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = usecase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAnException(){
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.clone(aCategory)));
        Mockito.when(categoryGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = usecase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_ShouldReturnNotFoundException(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(eq(CategoryID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> usecase.execute(aCommand));


        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));
        Mockito.verify(categoryGateway, times(0)).update(any());
    }
}
