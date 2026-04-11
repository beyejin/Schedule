package com.example.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Entity
@Table(name = "schedules")// 데이터 베이스 이름 설정
@NoArgsConstructor (access = AccessLevel.PROTECTED)// 기본생성자를 자동 생성 (jpa(공용 관리자)가 객체를 만들때 쓰는 빈 틀)
@EntityListeners(AuditingEntityListener.class)// 작성일, 수정일 필드는 JPA Auditing을 활용,엔티티의 생성 시간 / 수정 시간 / 작성자 등을 자동으로 기록해주는 기능
public class Schedule {
    //속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30, nullable = false)// nullable은 필수 값을 설정함
    private String title;
    @Column(length = 200, nullable = false)
    private String content;
    @Column(length = 30, nullable = false)
    private String author;
    @Column(length = 30, nullable = false)
    private String password;

    @CreatedDate
    @Column(updatable = false)// 처음 저장될 떄 자동으로 시간 저장
    private LocalDateTime createAt;
    @LastModifiedDate// 수정될 때마다 자동으로 시간 업데이트
    private LocalDateTime modifiedAt;

    //생

    public Schedule(String title, String content, String author, String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }


    //기
    public void update(String title, String author){
        this.title = title;
        this.author = author;
    }


}
