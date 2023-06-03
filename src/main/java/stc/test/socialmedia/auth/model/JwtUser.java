package stc.test.socialmedia.auth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import stc.test.socialmedia.base.model.HasIdAndEmail;

import java.util.Collection;

public class JwtUser extends JwtAuthenticationToken implements HasIdAndEmail {

    @Getter
    @Setter
    private Long id;

    public JwtUser(Jwt jwt, Collection<? extends GrantedAuthority> authorities, long id) {
        super(jwt, authorities);
        this.id = id;
    }

    @Override
    public String getEmail() {
        return getName();
    }

    @Override
    public String toString() {
        return "JwtUser:" + id + '[' + getName() + ']';
    }
}
