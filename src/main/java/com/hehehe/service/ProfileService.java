package com.hehehe.service;

import com.hehehe.model.entity.dto.ProfileDTO;
import com.hehehe.model.entity.dto.ProfileDTO.Response;
import com.hehehe.model.entity.dto.ProfileDTO.Request;
import com.hehehe.model.entity.dto.ProfileDTO.CommunityResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface  ProfileService {

    void create(Request request);

    //Page<Response> list(Pageable pageable);
    Response update(Long profileId, Request request);
    void delete(Long profileId);

    // 나의 화면
    Page<Response> list(int page, String sortBy, String category);

    // 커뮤니티 화면
    Page<CommunityResponse> communityList(int page, String sortBy, String category);
    void like(Request request, Long userId);

    void bookmark(Request request, Long userId);

    void likeCancel(Request request, Long userId);

    void bookmarkCancel(Request request, Long userId);

    List<ProfileDTO.MultiProfileResponse> profileList(Long userId);
}
