package com.example.Datenow.domain.common;

import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTime class를 상속할 경우 BaseTime class의 필드인 createdDate, modifiedDate를 인식하도록 합니다.
@EntityListeners(AuditingEntityListener.class) // EntityListeners란 JPA Entity에서 이벤트가 발생할 때마다 특정 로직을 실행시킬 수 있는 어노테이션입니다. // 이게 있어야 @CreatedDate 활성화
public class Date {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}