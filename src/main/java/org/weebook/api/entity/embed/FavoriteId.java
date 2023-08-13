package org.weebook.api.entity.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class FavoriteId implements Serializable {
    @Serial
    private static final long serialVersionUID = -466862574076301851L;
    @NotNull
    @Column(name = "\"userId\"", nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = "\"bookId\"", nullable = false)
    private Long bookId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FavoriteId entity = (FavoriteId) o;
        return Objects.equals(this.userId, entity.userId) &&
                Objects.equals(this.bookId, entity.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }

}