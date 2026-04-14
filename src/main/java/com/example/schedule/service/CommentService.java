package com.example.schedule.service;

import com.example.schedule.dto.CreateCommentRequest;
import com.example.schedule.dto.CreateCommentResponse;
import com.example.schedule.entity.Comment;
import com.example.schedule.repository.CommentRepository;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service // Service 빈으로 등록
@RequiredArgsConstructor // final 필드 자동으로 생성자를 만들어줌
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        // 댓글을 달 일정이 실제로 존재하는지 확인
        // -> 없으면 orElseThrow로 예외 발생
        scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        //1개의 일정에는 10개의 댓글까지만 가능
        List<Comment> commentList = commentRepository.findByScheduleId(scheduleId);
        if (commentList.size() >= 10) {
            throw new IllegalStateException("한 일정에는 댓글을 10개를 초과할 수 없습니다");
        }

        Comment comment = new Comment(request.getContent(), request.getAuthor(), request.getPassword(), scheduleId);

        Comment savedComment = commentRepository.save(comment);
        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getAuthor(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt(),
                savedComment.getScheduleId()
                );
    }

}
