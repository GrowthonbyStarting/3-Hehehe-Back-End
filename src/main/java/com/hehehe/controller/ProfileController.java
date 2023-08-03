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

    @PostMapping
    public void create(@RequestBody Request request) {
         profileService.create(request, request.getUserId());
    }

    @GetMapping("/community")
    public Page<CommunityResponse> communityList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sortBy", defaultValue = "new") String sortBy,
            @RequestParam(value = "category" , defaultValue = "default") String category ,
            @RequestParam(value = "user", required = false) Long user
    ) {
        return profileService.communityList(page -1 , sortBy , category, user);
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
         profileService.like(request, request.getUserId());
    }

    @DeleteMapping("/like")
    public void likeCancel(@RequestBody Request request) {
        profileService.likeCancel(request, request.getUserId());
    }

    @PostMapping("/bookmark")
    public void bookmark(@RequestBody Request request) {
        profileService.bookmark(request, request.getUserId());
    }

    @DeleteMapping("/bookmark")
    public void bookmarkCancel(@RequestBody Request request) {
        profileService.bookmarkCancel(request, request.getUserId());
    }

    @GetMapping("/multi")
    public List<ProfileDTO.MultiProfileResponse> profileList(
            @RequestParam(value = "user", required = false) Long user) {
        return profileService.profileList(user);
    }
    @GetMapping("/bookmark")
    public List<ProfileDTO.BookmarkProfileResponse> bookmarkProfileList(
            @RequestParam(value = "user", required = false)Long user ){
        return profileService.bookmarkProfileList(user);
    }

    @PutMapping("/category")
    public ProfileDTO.CategoryResponse category(@RequestBody Request request) {
        return profileService.category(request);
    }

    @PutMapping("/share")
    public ProfileDTO.ShareResponse  share(@RequestBody Request request) {
        return profileService.share(request);
    }
}
