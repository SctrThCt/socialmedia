package stc.test.socialmedia.base.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedTo extends BaseTo {
    @NotBlank(message = "name must not be blank")
    @Size(min = 2, max = 100, message = "name must be at least 2 symbols, no more than 100 symbold")
    protected String name;

    public NamedTo(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
