package stc.test.socialmedia.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import stc.test.socialmedia.base.model.NamedEntity;
import stc.test.socialmedia.user.model.User;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post extends NamedEntity {

    @JoinColumn (name = "user_id")
    @ManyToOne (fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "description")
    private String description;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date created = new Date();

    public Post(long id, String header, String description, User user) {
        super(id,header);
        this.description = description;
        this.user = user;
        this.photoPath = null;
    }
}
