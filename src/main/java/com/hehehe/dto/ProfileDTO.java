package com.hehehe.dto;


import com.hehehe.model.entity.ProfileEntity;
import lombok.*;

import java.time.LocalDateTime;

public class ProfileDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private String image;
        private String nickName;
        private String introduction;
        private String shareLink;
        private Boolean share;
        private String category;
        private String profileStatus;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private String image;
        private String nickName;
        private String introduction;
        private String shareLink;
        private String bookmark;
        private String likes;
        private String category;
        private Boolean share;
        private LocalDateTime updatedAt;
        private String profileStatus;

        public Response(ProfileEntity profileEntity) {
            this.image = profileEntity.getImage();
            this.nickName = profileEntity.getNickName();
            this.introduction = profileEntity.getIntroduction();
            this.shareLink = profileEntity.getShareLink();
            this.bookmark = null;
            this.likes = null;
            this.share = profileEntity.getShare();
            this.updatedAt = profileEntity.getUpdatedAt();
            this.profileStatus = profileEntity.getProfileStatus();
        }
    }
}
