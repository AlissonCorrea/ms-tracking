package com.ms.post.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String emailFrom;

    private String emailTo;

    private String subject;

    private String text;
}
