package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter

public class CreateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String password;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

    public CreateScheduleResponse(Schedule s) {
        this.id = s.getId();
        this.title = s.getTitle();
        this.content = s.getContent();
        this.author = s.getAuthor();
        this.password = s.getPassword();
        this.createAt = s.getCreateAt();
        this.modifiedAt = s.getModifiedAt();
    }


}
