package com.hehehe.service;

import com.hehehe.dto.ProfileDTO.Response;
import com.hehehe.dto.ProfileDTO.Request;
import org.springframework.data.domain.Page;

public interface  ProfileService {

    void create(Request request);

    //Page<Response> list(Pageable pageable);
    Response update(Long profileId, Request request);
    void delete(Long profileId);

    Page<Response> list(int page, int size, String sortBy, String category);
}
