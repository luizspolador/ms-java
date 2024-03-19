package br.com.spolador.catalog.admin;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

public class CleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        final var repositories = SpringExtension.getApplicationContext(extensionContext)
                .getBeansOfType(CrudRepository.class)
                .values();

        cleanUp(repositories);
    }

    private void cleanUp(final Collection<CrudRepository> rerepositories){
        rerepositories.forEach(CrudRepository::deleteAll);
    }
}
