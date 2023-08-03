package com.hehehe.repository;

import com.hehehe.model.entity.LikeEntity;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserAndProfile(UserEntity user, ProfileEntity profile);

    Long countByProfile(ProfileEntity profile);
}
