package com.ms.post.mapper;

import com.ms.post.dto.EmailDto;
import com.ms.post.enums.StatusPostageEnum;
import com.ms.post.model.PostageModel;
import org.springframework.stereotype.Component;

@Component
public class PostageModelToEmailPostageMapper {

    public EmailDto mapper(PostageModel postageModel) {
        var email = new EmailDto();
        email.setEmailTo(postageModel.getEmailDestination());
        email.setEmailFrom("*********@gmail.com");
        email.setSubject("Dados de Rastreamento Código - " + postageModel.getTrackingCode());

        if (postageModel.getStatus() == StatusPostageEnum.POSTAGE) {
            email.setText("Objeto postado - por favor aguarde");
        } else if (postageModel.getStatus() == StatusPostageEnum.IN_TRANSIT) {
            email.setText("Objeto em transito - por favor aguarde");
        } else {
            email.setText("Objeto entregue ao destinatário");
        }

        return email;
    }
}
