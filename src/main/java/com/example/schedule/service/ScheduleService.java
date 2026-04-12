package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;//레파지토리 가져옴

    //저장
    @Transactional
    public CreateScheduleResponse save(CreateScheduleRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getAuthor(),
                savedSchedule.getPassword(),
                savedSchedule.getCreateAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 단 건 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse getOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 스케줄입니다.")
        );
        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreateAt(),
                schedule.getModifiedAt()
        );
    }

    // 다 건 조회
    @Transactional(readOnly = true)
    public List<GetScheduleResponse> getAll(String author) {
        List<Schedule> schedules;
        // 작가 명으로 조회
        if (author != null) {
            schedules = scheduleRepository.findByAuthor(author); //findByAuthor가 필터

        }
        //전체 조회
        else {
            schedules = scheduleRepository.findAll();
        }

        List<GetScheduleResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            dtos.add(new GetScheduleResponse(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContent(),
                            schedule.getAuthor(),
                            schedule.getCreateAt(),
                            schedule.getModifiedAt()
                    )
            );
        }
        return dtos;
    }

    //업데이트
    @Transactional
    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("잃은 유저입니다.")
        );
        schedule.update(
                request.getTitle(),
                request.getAuthor()
        );

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreateAt(),
                schedule.getModifiedAt()
        );
    }

    //삭제 그디어!!! 악 아아아아아ㅏ
    @Transactional
    public void delete(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 스케줄입니다.")
        );
        if (!schedule.getPassword().equals(password)) {
            throw new IllegalStateException("비밀 번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }


}
