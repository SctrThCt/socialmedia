package stc.test.socialmedia.user.to;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import stc.test.socialmedia.base.model.HasIdAndEmail;
import stc.test.socialmedia.base.to.NamedTo;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RequestUserTo extends NamedTo implements HasIdAndEmail {
    @Email(message = "in email must enter email")
    @NotBlank(message = "email must not be blank")
    @Size(max = 100, message = "email must be shorter than 100 symbols")
    String email;

    @NotBlank (message = "password must not be blank")
    @Size(min = 5, max = 32, message = "must be at least 5 symbols and less than 32")
    String password;

    public RequestUserTo(Long id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
