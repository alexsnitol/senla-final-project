package ru.senla.realestatemarket.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.senla.realestatemarket.model.IModel;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
public class AuthorizedUser extends User implements IModel<Long> {
    
    private Long id;


    public AuthorizedUser(
            Long id,
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorizedUser)) return false;
        if (!super.equals(o)) return false;
        AuthorizedUser that = (AuthorizedUser) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

}
