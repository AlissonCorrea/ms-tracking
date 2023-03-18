package com.ms.tracking.service;

import com.ms.tracking.model.TrackingModel;
import com.ms.tracking.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackingService {

    @Autowired
    TrackingRepository trackingRepository;

    public TrackingModel insert(TrackingModel trackingModel) {
        return trackingRepository.save(trackingModel);
    }

    public TrackingModel findLastEventByTrackingCode(String trackingCode) {
        return trackingRepository.findLastEventByTrackingCode(trackingCode);
    }

    public List<TrackingModel> findAll() {
        return trackingRepository.findAll();
    }

    public List<TrackingModel> findAllByTrackCode(String trackingCode) {
        return trackingRepository.findAllByTrackingCode(trackingCode);
    }

    public Optional<TrackingModel> findById(Long id) {
        return trackingRepository.findById(id);
    }


}
