package com.graduation.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserScore {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String avatarUrl;

    private Double score;

    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long examId;
}
