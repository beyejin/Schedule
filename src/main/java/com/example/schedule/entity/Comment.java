package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter // 모든 필드에 대해한 getter 메서드를 자동 생성
@Entity // 이 클래스가 DB 테이블과 매핑되는 JPA 엔티티를 선언
@Table(name = "comments")// 실제 DB 테이블 이름을 "comments"로 지정
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 만들어줌
@EntityListeners(AuditingEntityListener.class)
public class Comment {


    //놀랍게도 의존성을 댓글이 가지고 있음 댓글이 게시물을 가지는거임!!
    @Column(nullable = false)
    private Long scheduleId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public Comment(String content, String author, String password, Long scheduleId) {
        this.content = content;
        this.author = author;
        this.password = password;
        this.scheduleId = scheduleId;
    }


}

