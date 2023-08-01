package com.hehehe.repository;

import com.hehehe.model.entity.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    List<ProfileEntity> findAllByShare(Boolean share);
    Page<ProfileEntity> findAllByShareAndCategory(Boolean share, String category, Pageable pageable);
}
