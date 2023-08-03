package com.hehehe.repository;

import com.hehehe.model.entity.BookmarkEntity;
import com.hehehe.model.entity.ProfileEntity;
import com.hehehe.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    Optional<BookmarkEntity> findByUserAndProfile(UserEntity user , ProfileEntity profile);
}
