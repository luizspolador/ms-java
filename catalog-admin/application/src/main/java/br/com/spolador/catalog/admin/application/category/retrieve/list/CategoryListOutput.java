package br.com.spolador.catalog.admin.application.category.retrieve.list;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryID;

import java.time.Instant;

public record CategoryListOutput(
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt
) {

    public static CategoryListOutput from (final Category aCatagory){
        return new CategoryListOutput(
                aCatagory.getId(),
                aCatagory.getName(),
                aCatagory.getDescription(),
                aCatagory.isActive(),
                aCatagory.getCreatedAt(),
                aCatagory.getDeletedAt()
        );
    }
}
