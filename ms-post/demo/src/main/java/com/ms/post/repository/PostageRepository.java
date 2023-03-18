package com.ms.post.repository;

import com.ms.post.model.PostageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostageRepository extends JpaRepository<PostageModel, Long> {
    @Query("FROM PostageModel t WHERE t.trackingCode = :trackingCode")
    PostageModel findByTrackingCode(String trackingCode);
}
