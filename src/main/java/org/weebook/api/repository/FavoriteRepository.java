package org.weebook.api.repository;

public interface FavoriteRepository extends org.springframework.data.jpa.repository.JpaRepository<org.weebook.api.entity.Favorite, org.weebook.api.entity.embed.FavoriteId> {
}