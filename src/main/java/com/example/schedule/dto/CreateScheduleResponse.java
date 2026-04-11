package com.example.schedule.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor// 생성자 만들어줌 차카니~
public class CreateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String password;
    private final LocalDateTime createAt;
    private final LocalDateTime modifiedAt;

}
