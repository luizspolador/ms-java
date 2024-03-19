package br.com.spolador.catalog.admin.infrastructure.category;

import br.com.spolador.catalog.admin.domain.category.Category;
import br.com.spolador.catalog.admin.domain.category.CategoryGateway;
import br.com.spolador.catalog.admin.domain.category.CategoryID;
import br.com.spolador.catalog.admin.domain.category.CategorySearchQuery;
import br.com.spolador.catalog.admin.domain.pagination.Pagination;
import br.com.spolador.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.spolador.catalog.admin.infrastructure.category.persistence.CategoryRepository;
import br.com.spolador.catalog.admin.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.Optional;

import static br.com.spolador.catalog.admin.infrastructure.utils.SpecificationUtils.like;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(final CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public void deleteById(final CategoryID anId) {
        final String anIdValue = anId.getValue();
        if(this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }

    }

    @Override
    public Optional<Category> findById(final CategoryID anId) {
        return this.repository.findById(anId.getValue())
                .map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery aQuery) {
        //Page
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //Busca dinâmica pelo critério terms (name or description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> {
                    return SpecificationUtils
                            .<CategoryJpaEntity>like("name", str)
                            .or(like("description", str));
                })
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specifications), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }

    private Category save (final Category aCategory){
        return this.repository.save(CategoryJpaEntity.from(aCategory)).toAggregate();
    }
}
