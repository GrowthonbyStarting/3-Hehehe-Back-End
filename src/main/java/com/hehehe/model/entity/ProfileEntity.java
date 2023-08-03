package com.hehehe.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
@Table(name = "profile_table")
public class ProfileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id" ,nullable = false, updatable = false)
    private Long id;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '닉네임'")
    private String nickName;

    @Column(columnDefinition = "varchar(500) DEFAULT NULL COMMENT '소개'")
    private String introduction;

    @Column(columnDefinition = "bit default false NOT NULL COMMENT '공유 여부'")
    private Boolean share;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '공유 링크'")
    private String shareLink;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '이미지 주소'")
    private String image;
    
    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '카테고리'")
    private String category;

    @Column(columnDefinition = "bit default false NOT NULL COMMENT '현재 프로필 상태'")
    private Boolean profileStatus;

    private Long userId;
}
