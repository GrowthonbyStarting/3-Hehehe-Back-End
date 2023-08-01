package com.hehehe.controller;

import com.hehehe.dto.ResponseDTO;
import com.hehehe.dto.ProfileDTO.Response;
import com.hehehe.dto.ProfileDTO.Request;

import com.hehehe.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import static com.hehehe.dto.ResponseDTO.ok;

@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        profileService.create(request);
        return ok();
    }


    // 커뮤니티 화면에서 보여줄 List
    @GetMapping
    public ResponseDTO<Page<Response>> list(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("category") String category
             ) {
        return ok(profileService.list(page -1 , size, sortBy , category));

    }

    @PutMapping("/{profileId}")
    public ResponseDTO<Response> update( @PathVariable Long profileId, @RequestBody Request request) {
        return ok(profileService.update(profileId,request));
    }

    @DeleteMapping("/{profileId}")
    public ResponseDTO<Response> delete(@PathVariable Long profileId) {
        profileService.delete(profileId);
        return ok();
    }
}
