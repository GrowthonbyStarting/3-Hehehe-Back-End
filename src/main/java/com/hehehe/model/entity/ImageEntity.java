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
@Table(name = "image")
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(columnDefinition = "varchar(225) DEFAULT NULL COMMENT '이미지 주소'")
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "id")
    private ProfileEntity profile;

}
