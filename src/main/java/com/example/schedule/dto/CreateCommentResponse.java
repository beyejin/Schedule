package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final String content;
    private final String author;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;
    private final Long scheduleId;

    public CreateCommentResponse(Long id, String content, String author, LocalDateTime createAt, LocalDateTime modifiedAt, Long scheduleId) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
        this.scheduleId = scheduleId;
    }
}
