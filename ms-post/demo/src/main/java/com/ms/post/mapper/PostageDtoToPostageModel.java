package com.ms.post.mapper;

import com.ms.post.dto.PostageDto;
import com.ms.post.model.PostageModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostageDtoToPostageModel {

    public PostageModel mapper(PostageDto postageDto) {
        PostageModel postageModel = new PostageModel();
        BeanUtils.copyProperties(postageDto, postageModel);
        return postageModel;
    }
}
