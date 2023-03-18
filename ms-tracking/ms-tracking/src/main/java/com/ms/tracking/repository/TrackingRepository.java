package com.ms.tracking.repository;

import com.ms.tracking.enums.StatusPostageEnum;
import com.ms.tracking.model.TrackingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingModel, Long> {

    @Query("FROM TrackingModel t WHERE t.trackingCode = :trackingCode ORDER BY t.id DESC LIMIT 1")
    TrackingModel findLastEventByTrackingCode(String trackingCode);

    List<TrackingModel> findAllByTrackingCode(String trackingCode);
}
