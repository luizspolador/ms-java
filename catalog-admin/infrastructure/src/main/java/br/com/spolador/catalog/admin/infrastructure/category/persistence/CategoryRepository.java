package br.com.spolador.catalog.admin.infrastructure.category.persistence;

import br.com.spolador.catalog.admin.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
