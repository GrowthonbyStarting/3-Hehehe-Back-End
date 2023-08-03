package com.hehehe.controller;


import com.hehehe.model.entity.dto.ProfileDTO;
import com.hehehe.model.entity.dto.ProfileDTO.Response;
import com.hehehe.model.entity.dto.ProfileDTO.Request;
import com.hehehe.model.entity.dto.ProfileDTO.CommunityResponse;

import com.hehehe.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = {"https://3-hehehe-front-end.vercel.app","http://localhost:3000"})
//@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProfileController {

    private final ProfileService profileService;


    private final Long userId = 2L;

    @PostMapping
    public void create(@RequestBody Request request) {
         profileService.create(request);
    }

    @GetMapping
    public Page<Response> list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "new") String sortBy,
            @RequestParam(value = "category" , required = false) String category
             ) {
        return profileService.list(page -1 , sortBy , category);

    }

    @GetMapping("/community")
    public Page<CommunityResponse> communityList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "new") String sortBy,
            @RequestParam(value = "category" , defaultValue = "default") String category
    ) {
        return profileService.communityList(page -1 , sortBy , category);
    }



    @PutMapping("/{profileId}")
    public Response update( @PathVariable Long profileId, @RequestBody Request request) {
        return profileService.update(profileId,request);
    }

    @DeleteMapping("/{profileId}")
    public void delete(@PathVariable Long profileId) {
        profileService.delete(profileId);
    }

    @PostMapping("/like")
    public void like(@RequestBody Request request) {
         profileService.like(request, userId);
    }

    @DeleteMapping("/like")
    public void likeCancel(@RequestBody Request request) {
        profileService.likeCancel(request, userId);
    }

    @PostMapping("/bookmark")
    public void bookmark(@RequestBody Request request) {
        profileService.bookmark(request, userId);
    }

    @DeleteMapping("/bookmark")
    public void bookmarkCancel(@RequestBody Request request) {
        profileService.bookmarkCancel(request, userId);
    }

    // ToDO: 다중 프로필
    @GetMapping("/multi")
    public List<ProfileDTO.MultiProfileResponse> profileList(
    ) {
        return profileService.profileList(userId);
    }

    // ToDO: 북마크 프로필
//    @GetMapping("/community")
//    public Page<CommunityResponse> communityList(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "sortBy", defaultValue = "new") String sortBy,
//            @RequestParam(value = "category" , defaultValue = "default") String category
//    ) {
//        return profileService.communityList(page -1 , sortBy , category);
//    }

}
