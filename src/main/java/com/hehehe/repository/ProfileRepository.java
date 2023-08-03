package com.hehehe.repository;

import com.hehehe.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    List<ProfileEntity> findAllByShare(Boolean share);
    //Page<ProfileEntity> findAllByShare(Boolean share, Pageable pageable);
    //Page<ProfileEntity> findAllByShareAndCategory(Boolean share, String category, Pageable pageable);

    List<ProfileEntity> findAllByUserId(Long userId);

    ProfileEntity findByIdAndUserId(Long profileId, Long userId);
}
