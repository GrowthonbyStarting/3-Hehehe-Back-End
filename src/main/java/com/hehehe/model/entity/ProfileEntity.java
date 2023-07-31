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
@Table(name = "profile")
public class ProfileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id" ,nullable = false, updatable = false)
    private Long id;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '닉네임'")
    private String nickNam;

    @Column(columnDefinition = "varchar(500) DEFAULT NULL COMMENT '소개'")
    private String introduction;

    @Column(columnDefinition = "varchar(5) DEFAULT NULL COMMENT '공유여부'")
    private String share;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '공유링크'")
    private String shareLink;

    @OneToOne(mappedBy = "profile")
    private ImageEntity image;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '카테고리'")
    private String category;


}
