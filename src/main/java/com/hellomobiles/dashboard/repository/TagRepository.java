package com.hellomobiles.dashboard.repository;

import com.hellomobiles.dashboard.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTagName(String tagName);

}