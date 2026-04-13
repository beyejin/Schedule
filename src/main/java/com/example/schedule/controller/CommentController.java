package com.example.schedule.controller;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.service.CommentService;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController// http 요청 받는 컨트롤러 컴포넌트 스켄 기능이 들어있음-> 스프링 빈으로 등록
// 반환 값을 JSON으로 변환해 줌 왜냐면 http는 응답값이 객체면 알아듣지 못함
@RequiredArgsConstructor // final 필드 자동으로 생성자 만들어줌
@RequestMapping("/schedules/{scheduleId}/comments")//자동으로 모든 url 앞에 schedule 붙여줌 클래스 레벨에 적어야 유지보수에 좋음
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(
            @RequestBody CreateCommentRequest request, @PathVariable Long scheduleId){
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId,request));
    }

}
