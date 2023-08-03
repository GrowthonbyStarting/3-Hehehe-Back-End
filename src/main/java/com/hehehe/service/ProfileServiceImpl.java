package com.hehehe.service;

import com.hehehe.model.entity.BookmarkEntity;
import com.hehehe.model.entity.LikeEntity;
import com.hehehe.model.entity.UserEntity;
import com.hehehe.model.entity.dto.ProfileDTO;
import com.hehehe.exception.BaseException;
import com.hehehe.exception.ResultType;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.repository.BookmarkRepository;
import com.hehehe.repository.LikeRepository;
import com.hehehe.repository.ProfileRepository;
import com.hehehe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public void create(ProfileDTO.Request request, Long userId) {

        ProfileEntity profile = modelMapper.map(request, ProfileEntity.class);
        profile.setShareLink("wity.im/");
        profile.setShare(false);
        profile.setProfileStatus(false);
        profile.setCategory("");
        profile.setUserId(userId);
        profileRepository.save(profile);
    }

    private Pageable getPageable(int page, String sortBy) {
        Sort.Direction direction = Sort.Direction.DESC;

        if (sortBy.equals("popular")) {
            sortBy = "popular";
        } else {
            sortBy = "new";
        }

        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, 100, sort);
    }

    public Page<ProfileDTO.Response> overPages(List<ProfileDTO.Response> profileList, int start, int end, Pageable pageable, int page) {
        Page<ProfileDTO.Response> pages = new PageImpl<>(profileList.subList(start, end), pageable, profileList.size());
        if (page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    public Page<ProfileDTO.CommunityResponse> overPages1(List<ProfileDTO.CommunityResponse> profileList, int start, int end, Pageable pageable, int page) {
        Page<ProfileDTO.CommunityResponse> pages = new PageImpl<>(profileList.subList(start, end), pageable, profileList.size());
        if (page > pages.getTotalPages()) {
            throw new IllegalArgumentException("요청할 수 없는 페이지 입니다.");
        }
        return pages;
    }

    @Override
    @Transactional
    public ProfileDTO.Response update(Long profileId, ProfileDTO.Request request) {
        ProfileEntity updateProfile = profileRepository.findById(profileId).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        updateProfile.setImage(request.getImage());
        updateProfile.setNickName(request.getNickName());
        updateProfile.setIntroduction(request.getIntroduction());
        updateProfile.setShareLink(request.getShareLink());
        updateProfile.setCategory(request.getCategory());
        updateProfile.setProfileStatus(request.getProfileStatus());
        updateProfile.setShare(request.getShare());

        ProfileEntity updatedProfile = profileRepository.save(updateProfile);

        long time = Timestamp.valueOf(updatedProfile.getUpdatedAt()).getTime();

        ProfileDTO.Response response = ProfileDTO.Response.builder()
                .profileId(profileId)
                .image(updateProfile.getImage())
                .nickName(updateProfile.getNickName())
                .introduction(updateProfile.getIntroduction())
                .shareLink(updateProfile.getShareLink())
                .category(updateProfile.getCategory())
                .profileStatus(updateProfile.getProfileStatus())
                .share(updateProfile.getShare())
                .updatedAt(time)
                .build();

        return response;
    }

    @Override
    @Transactional
    public void delete(Long profileId) {
        ProfileEntity profile = profileRepository.findById(profileId).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        profile.setIsDeleted(true);
        profileRepository.save(profile);
    }

    @Override
    @Transactional
    public void like(ProfileDTO.Request request, Long userId) {
        ProfileEntity profile = profileRepository.findById(request.getProfileId()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 프로필 입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));

        // 두 번 좋아요를 클릭 할 수 없도록 유효성 검사
        Optional<LikeEntity> like = likeRepository.findByUserAndProfile(user, profile);

        if (like.isPresent()) {
            new IllegalArgumentException("좋아요는 한번만 클릭 가능");
        }


        LikeEntity likes = new LikeEntity();
        likes.setProfile(profile);
        likes.setUser(user);

        likeRepository.save(likes);
    }

    @Override
    @Transactional
    public void likeCancel(ProfileDTO.Request request, Long userId) {

        ProfileEntity profile = profileRepository.findById(request.getProfileId()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 프로필 입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));

        Optional<LikeEntity> like = likeRepository.findByUserAndProfile(user, profile);

        if (like.isPresent()) {
            likeRepository.deleteById(like.get().getId());
//            likeRepository.deleteAll();
        }
    }

    @Override
    public void bookmark(ProfileDTO.Request request, Long userId) {
        ProfileEntity profile = profileRepository.findById(request.getProfileId()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 프로필 입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));
        Optional<BookmarkEntity> bookmarkEntity = bookmarkRepository.findByUserAndProfile(user, profile);

        if (bookmarkEntity.isPresent()) {
            bookmarkRepository.deleteById(bookmarkEntity.get().getId());
//            bookmarkRepository.deleteAll();
        }
        BookmarkEntity bookmark = new BookmarkEntity();
        bookmark.setProfile(profile);
        bookmark.setUser(user);

        bookmarkRepository.save(bookmark);
    }

    @Override
    @Transactional
    public void bookmarkCancel(ProfileDTO.Request request, Long userId) {

        ProfileEntity profile = profileRepository.findById(request.getProfileId()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 프로필 입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));

        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndProfile(user, profile);

        if (bookmark.isPresent()) {
            bookmarkRepository.deleteById(bookmark.get().getId());
//            bookmarkRepository.deleteAll();
        }
    }

    @Override
    public Page<ProfileDTO.CommunityResponse> communityList(int page, String sortBy, String category, Long userId) {
        // sortBy ( 좋아요 순 , 최신순 ( 수정 시간 기준)
        Pageable pageable = getPageable(page, sortBy);
        List<ProfileEntity> profileEntity = profileRepository.findAllByShare(true);
        List<ProfileDTO.CommunityResponse> responseList = new ArrayList<>();


        for (ProfileEntity profile : profileEntity) {

            if (!category.isEmpty()) {
                System.out.println("===================");
            }

            UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                    new IllegalArgumentException("존재 하지 않는 유저 입니다."));

            Long totalCountLike = likeRepository.countByProfile(profile);
            Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndProfile(user, profile);

            boolean bookmarkStatus = false;

            if (bookmark.isPresent()) {
                bookmarkStatus = true;
            }

            long time = Timestamp.valueOf(profile.getUpdatedAt()).getTime();

            ProfileDTO.CommunityResponse response = ProfileDTO.CommunityResponse.builder()
                    .profileId(profile.getId())
                    .bookmark(bookmarkStatus)
                    .likes(Math.toIntExact(totalCountLike))
                    .category(profile.getCategory())
                    .updatedAt(time)
                    .build();

            responseList.add(response);

        }

        int start = page * 100;
        int end = Math.min((start + 100), profileEntity.size());

        return overPages1(responseList, start, end, pageable, page);
    }

    @Override
    public List<ProfileDTO.MultiProfileResponse> profileList(Long userId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));
        List<ProfileEntity> profileList = profileRepository.findAllByUserId(userId);
        List<ProfileDTO.MultiProfileResponse> multiProfileResponses = new ArrayList<>();

        for (ProfileEntity profile : profileList) {

            Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndProfile(user, profile);

            boolean bookmarkStatus = false;

            if (bookmark.isPresent()) {
                bookmarkStatus = true;
            }

            ProfileDTO.MultiProfileResponse multiProfileResponse = ProfileDTO.MultiProfileResponse.builder()
                    .profileId(profile.getId())
                    .image(profile.getImage())
                    .nickName(profile.getNickName())
                    .category(profile.getCategory())
                    .profileStatus(profile.getProfileStatus())
                    .share(profile.getShare())
                    .build();

            multiProfileResponses.add(multiProfileResponse);
        }
        return multiProfileResponses;
    }

    @Override
    public List<ProfileDTO.BookmarkProfileResponse> bookmarkProfileList(Long userId) {

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));
        List<ProfileEntity> profileList = profileRepository.findAll();
        List<ProfileDTO.BookmarkProfileResponse> bookmarkProfileResponseList = new ArrayList<>();

        for (ProfileEntity profile : profileList) {
            Long totalCountLike = likeRepository.countByProfile(profile);
            Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndProfile(user, profile);

            if (bookmark.isPresent()) {
                ProfileDTO.BookmarkProfileResponse bookmarkProfileResponse = ProfileDTO.BookmarkProfileResponse.builder()
                        .profileId(profile.getId())
                        .image(profile.getImage())
                        .nickName(profile.getNickName())
                        .category(profile.getCategory())
                        .shareLink(profile.getShareLink())
                        .bookmark(true)
                        .likes(Math.toIntExact(totalCountLike))
                        .build();
                bookmarkProfileResponseList.add(bookmarkProfileResponse);
            }
        }
        return bookmarkProfileResponseList;
    }

    @Override
    @Transactional
    public ProfileDTO.CategoryResponse category(ProfileDTO.Request request) {

        ProfileEntity updateProfile = profileRepository.findById(request.getProfileId()).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        updateProfile.setId(request.getProfileId());
        updateProfile.setCategory(request.getCategory());

        profileRepository.save(updateProfile);

        ProfileDTO.CategoryResponse response = ProfileDTO.CategoryResponse.builder()
                .profileId(request.getProfileId())
                .category(updateProfile.getCategory())
                .build();

        return response;
    }

    @Override
    @Transactional
    public ProfileDTO.ShareResponse share(ProfileDTO.Request request) {

        userRepository.findById(request.getProfileId()).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        ProfileEntity updateProfile = profileRepository.findByIdAndUserId(request.getProfileId(), request.getUserId());

        updateProfile.setId(request.getProfileId());
        updateProfile.setShare(request.getShare());

        ProfileEntity updatedProfile = profileRepository.save(updateProfile);

        ProfileDTO.ShareResponse response = ProfileDTO.ShareResponse.builder()
                .profileId(request.getProfileId())
                .share(updateProfile.getShare())
                .build();

        return response;
    }
}
