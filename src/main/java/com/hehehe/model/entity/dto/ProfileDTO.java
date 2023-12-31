package com.hehehe.model.entity.dto;


import com.hehehe.model.entity.ProfileEntity;
import lombok.*;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProfileDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class Request {

        private Long userId;
        private Long profileId;
        private String image;
        private String nickName;
        private String introduction;
        private String shareLink;
        private Boolean share;
        private String category;
        private Boolean profileStatus;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class Response {

        private Long profileId;
        private String image;
        private String nickName;
        private String introduction;
        private String shareLink;
        //        private Boolean bookmark;
        private int likes;
        private String category;
        private Boolean share;
        private long updatedAt;
        private Boolean profileStatus;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class ShareResponse {

        private Long profileId;
        private Boolean share;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class CategoryResponse {

        private Long profileId;
        private String category;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class CommunityResponse {
        private String nickName;
        private String shareLink;
        private Long profileId;
        private Boolean bookmark;
        private int likes;
        private String category;
        private long updatedAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class MultiProfileResponse {
        private Long profileId;
        private String image;
        private String nickName;
        private String category;
        private Boolean profileStatus;
        private Boolean share;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @ToString
    public static class BookmarkProfileResponse {
        private Long profileId;
        private String image;
        private String nickName;
        private String category;
        private String shareLink;
        private Boolean bookmark;
        private int likes;
    }
}
