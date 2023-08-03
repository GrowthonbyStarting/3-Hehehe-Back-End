package com.hehehe.service;

import com.hehehe.model.entity.BookmarkEntity;
import com.hehehe.model.entity.LikeEntity;
import com.hehehe.model.entity.UserEntity;
import com.hehehe.model.entity.dto.ProfileDTO;
import com.hehehe.exception.BaseException;
import com.hehehe.exception.ResultType;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.model.entity.dto.UserDTO;
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
    public void create(ProfileDTO.Request request) {

        ProfileEntity profile = modelMapper.map(request, ProfileEntity.class);
        profile.setShareLink("wity.im/");
        profile.setShare(false);
        profile.setProfileStatus(false);
        profile.setCategory("");
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
        return PageRequest.of(page, 5, sort);
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
    public Page<ProfileDTO.Response> list(int page, String sortBy, String category) {

        // sortBy ( 좋아요 순 , 최신순 ( 수정 시간 기준)
        Pageable pageable = getPageable(page, sortBy);

        List<ProfileEntity> profileEntity = profileRepository.findAllByShare(true);

        List<ProfileDTO.Response> responseList = new ArrayList<>();


        for (ProfileEntity profile : profileEntity) {

            Long totalCountLike = likeRepository.countByProfile(profile);

            ProfileDTO.Response response = ProfileDTO.Response.builder()
                    .profileId(profile.getId())
                    .image(profile.getImage())
                    .nickName(profile.getNickName())
                    .introduction(profile.getIntroduction())
                    .shareLink(profile.getShareLink())
//                    .bookmark()
                    .likes(Math.toIntExact(totalCountLike))
                    .category(profile.getCategory())
                    .share(profile.getShare())
                    .updatedAt(profile.getUpdatedAt())
                    .profileStatus(profile.getProfileStatus())
                    .build();

            responseList.add(response);

        }

        int start = page * 5;
        int end = Math.min((start + 5), profileEntity.size());

        System.out.println(end);
        return overPages(responseList, start, end, pageable, page);
    }


    @Override
    @Transactional
    public ProfileDTO.Response update(Long profileId, ProfileDTO.Request request) {
        ProfileEntity updateProfile = profileRepository.findById(profileId).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        updateProfile.setId(updateProfile.getId());
        updateProfile.setImage(request.getImage());
        updateProfile.setNickName(request.getNickName());
        updateProfile.setIntroduction(request.getIntroduction());
        updateProfile.setShareLink(request.getShareLink());
        updateProfile.setCategory(request.getCategory());
        updateProfile.setProfileStatus(request.getProfileStatus());
        updateProfile.setShare(request.getShare());

        ProfileEntity updatedProfile = profileRepository.save(updateProfile);
        return modelMapper.map(updatedProfile, ProfileDTO.Response.class);
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

        LikeEntity like = new LikeEntity();
        like.setProfile(profile);
        like.setUser(user);

        likeRepository.save(like);
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
        }
//        } else {
//            return new IllegalArgumentException("좋아요를 추가한 프로필이 없습니다.");
//        }
    }

    @Override
    public void bookmark(ProfileDTO.Request request, Long userId) {
        ProfileEntity profile = profileRepository.findById(request.getProfileId()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 프로필 입니다."));

        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));

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

        Optional<BookmarkEntity> like = bookmarkRepository.findByUserAndProfile(user, profile);

        if (like.isPresent()) {
            bookmarkRepository.deleteById(like.get().getId());
        }
//        } else {
//            return new IllegalArgumentException("북마크한 프로필이 없습니다.");
//        }
    }

    @Override
    public Page<ProfileDTO.CommunityResponse> communityList(int page, String sortBy, String category) {
        // sortBy ( 좋아요 순 , 최신순 ( 수정 시간 기준)
        Pageable pageable = getPageable(page, sortBy);
        List<ProfileEntity> profileEntity = profileRepository.findAllByShare(true);
        List<ProfileDTO.CommunityResponse> responseList = new ArrayList<>();
        Long userId = 1L;

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

        int start = page * 5;
        int end = Math.min((start + 5), profileEntity.size());

        return overPages1(responseList, start, end, pageable, page);
    }

    @Override
    public List<ProfileDTO.MultiProfileResponse> profileList(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저 입니다."));

        List<ProfileEntity> profileList = user.getProfileEntityList();
        List<ProfileDTO.MultiProfileResponse> multiProfileResponses = new ArrayList<>();

        System.out.println(profileList.size());

        for (ProfileEntity profile : profileList) {

            ProfileDTO.MultiProfileResponse multiProfileResponse = ProfileDTO.MultiProfileResponse.builder()
                    .profileId(profile.getId())
                    .image(profile.getImage())
                    .nickName(profile.getNickName())
                    .category(profile.getCategory())
                    .shareLink(profile.getShareLink())
                    .profileStatus(profile.getProfileStatus())
                    .build();

            multiProfileResponses.add(multiProfileResponse);
        }
        return multiProfileResponses;

    }
}
