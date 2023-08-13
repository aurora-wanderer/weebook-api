package org.weebook.api.repository;

public interface ReviewRepository extends org.springframework.data.jpa.repository.JpaRepository<org.weebook.api.entity.Review, org.weebook.api.entity.embed.ReviewId> {
}