package br.com.spolador.catalog.admin.domain.category;

import br.com.spolador.catalog.admin.domain.AggregateRoot;
import br.com.spolador.catalog.admin.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> implements Cloneable{
    private String name;
    private String description;
    private boolean active;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(CategoryID anId,
                    final String aName,
                    final String aDescription,
                    final boolean isActive,
                    final Instant aCreationDate,
                    final Instant aUpdateDate,
                    final Instant aDeleteDate
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(aCreationDate, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(aUpdateDate, "'updatedAt' should not be null");
        this.deletedAt = aDeleteDate;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isactive){
        final var id = CategoryID.unique(); // método unique gera um novo id
        final var now = Instant.now();
        final var deletedAt = isactive ? null : now;
        return new Category(id, aName, aDescription, isactive, now, now, deletedAt);
    }

    public static Category with(
            final CategoryID anId,
            final String name,
            String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category clone(final Category aCategory){
        return with(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }



    @Override
    public void validate(final ValidationHandler handler){
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate(){
        if(getDeletedAt() == null){
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate(){
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(final String aName, final String aDescription, final boolean isActive){
        this.name = aName;
        this.description = aDescription;
        if(isActive){
            activate();
        } else {
            deactivate();
        }
        this.updatedAt = Instant.now();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }


    @Override
    public Category clone() {
        try {
            Category clone = (Category) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
