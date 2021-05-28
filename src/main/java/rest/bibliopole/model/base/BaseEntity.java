package rest.bibliopole.model.base;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public abstract class BaseEntity implements ITransfer {

    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    protected Long id;

    @Version
    private Long version;

    @Override
    public String toString() {
        return String.format("Entity %s (%s", getClass().getName(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!getClass().isInstance(o)) return false;
        return getId() != null && getId().equals(((BaseEntity) o).getId());
    }

    @Override
    public int hashCode() {return 31;}
}
