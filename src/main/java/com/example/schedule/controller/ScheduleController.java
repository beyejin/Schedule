package com.example.schedule.controller;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// http 요청 받는 컨트롤러
@RequiredArgsConstructor // final 필드 자동으로 생성자 만들어줌
@RequestMapping("/schedules")//자동으로 모든 url 앞에 schedule 붙여줌
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }


    // 단 건 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long scheduleId) {
        GetScheduleResponse result = scheduleService.getOne(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 다 건 조회
    @GetMapping
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedules(@RequestParam(required = false) String author) { // RequestParam이 url 뒤에서 값을 꺼내옴  required = false이건 옵셔널 처럼 없어도 된다는 뜻
        List<GetScheduleResponse> reslut = scheduleService.getAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(reslut);
    }

    // 업데이트
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request
    ) {
        UpdateScheduleResponse result = scheduleService.update(scheduleId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 삭제
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId,
            @RequestBody DeleteScheduleRequest request
    ) {
        scheduleService.delete(scheduleId, request.getPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

