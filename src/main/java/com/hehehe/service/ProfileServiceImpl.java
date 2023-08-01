package com.hehehe.service;

import com.hehehe.dto.ProfileDTO;
import com.hehehe.exception.BaseException;
import com.hehehe.exception.ResultType;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    public void create(ProfileDTO.Request request) {

        ProfileEntity profile = modelMapper.map(request, ProfileEntity.class);
        profile.setCategory("온라인 매장");
        profile.setShareLink("wity.im/");
        ProfileEntity saveProfile = profileRepository.save(profile);

    }

    @Override
    public Page<ProfileDTO.Response> list(int page, int size, String sortBy, String category) {

        // sortBy ( 좋아요 순 , 최신순 ( 수정 시간 기준)
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<ProfileEntity> profileList = profileRepository.findAllByShare(true);

        for(ProfileEntity profile : profileList ) {
            if (profile.getCategory().equals("크리에이터")){
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "크리에이터",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("온라인 매장")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "온라인 매장",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("1인 기업")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "1인 기업",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("공동 구매")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "공동 구매",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("카페")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "카페",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("자기개발")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "자기개발",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else if (profile.getCategory().equals("포트폴리오")) {
                Page<ProfileEntity> categoryProfile = profileRepository.findAllByShareAndCategory(true, "포트폴리오",pageable);
                return categoryProfile.map(ProfileDTO.Response::new);
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
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

        profileRepository.save(updateProfile);

        return modelMapper.map(updateProfile, ProfileDTO.Response.class);
    }

    @Override
    public void delete(Long profileId) {
        ProfileEntity profile = profileRepository.findById(profileId).orElseThrow(
                () -> new BaseException(ResultType.NOT_EXIST));

        profile.setIsDeleted(true);
        profileRepository.save(profile);
    }
}
