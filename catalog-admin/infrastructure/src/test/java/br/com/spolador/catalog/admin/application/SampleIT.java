package br.com.spolador.catalog.admin.application;

import br.com.spolador.catalog.admin.IntegrationTest;
import br.com.spolador.catalog.admin.application.category.create.CreateCategoryUseCase;
import br.com.spolador.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIT {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void test(){
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(categoryRepository);
    }
}
