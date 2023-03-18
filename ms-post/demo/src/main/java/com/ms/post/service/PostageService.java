package com.ms.post.service;

import com.ms.post.enums.StatusPostageEnum;
import com.ms.post.model.PostageModel;
import com.ms.post.repository.PostageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PostageService {

    @Autowired
    PostageRepository postageRepository;

    public PostageModel insert(PostageModel postageModel) {

        postageModel.setStatus(StatusPostageEnum.POSTAGE);
        var trackingCode = this.generateTrackingCode(postageModel);
        postageModel.setTrackingCode(trackingCode + "BR");

        return postageRepository.save(postageModel);
    }

    public PostageModel alterStatus(PostageModel postageModel, StatusPostageEnum status) {
        postageModel.setStatus(status);
        return postageRepository.save(postageModel);
    }

    public PostageModel findByTrackingCode(String trackingCode) {
        return postageRepository.findByTrackingCode(trackingCode);
    }

    public List<PostageModel> findAll() {
        return postageRepository.findAll();
    }

    public Optional<PostageModel> findById(Long id) {
        return postageRepository.findById(id);
    }

    public String generateTrackingCode(PostageModel postageModel) {

        var value = postageModel.getEmailDestination()
                + postageModel.getZipCodeDestination()
                + postageModel.getValue();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
        return hash.toString(20);
    }
}
