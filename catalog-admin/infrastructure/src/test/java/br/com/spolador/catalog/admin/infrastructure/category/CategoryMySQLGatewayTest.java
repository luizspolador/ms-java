package br.com.spolador.catalog.admin.infrastructure.category;

import br.com.spolador.catalog.admin.infrastructure.MySQLGatewayTest;
import br.com.spolador.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;



    @Test
    public void testInjectedDependencies(){
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(categoryRepository);
    }
}
