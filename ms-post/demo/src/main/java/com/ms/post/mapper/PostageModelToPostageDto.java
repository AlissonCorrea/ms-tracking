package com.ms.post.mapper;

import com.ms.post.dto.PostageDto;
import com.ms.post.model.PostageModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostageModelToPostageDto {

    public PostageDto mapper(PostageModel postageModel) {
        PostageDto postageDto = new PostageDto();
        BeanUtils.copyProperties(postageModel, postageDto);
        return postageDto;
    }
}
