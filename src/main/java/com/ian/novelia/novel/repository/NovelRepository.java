package com.ian.novelia.novel.repository;

import com.ian.novelia.novel.domain.Category;
import com.ian.novelia.novel.domain.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    @Query("""
            select n
            from Novel n
            join n.user u
            where (:category is null or n.category = :category)
              and (
                    :keyword is null or :keyword = ''
                    or (:searchType = 'TITLE' and n.title like concat('%', :keyword, '%'))
                    or (:searchType = 'DESCRIPTION' and n.description like concat('%', :keyword, '%'))
                    or (:searchType = 'AUTHOR' and u.penName like concat('%', :keyword, '%'))
              )
              and n.deletedAt is null
            """)
    Page<Novel> searchNovels(
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("category") Category category,
            Pageable pageable
    );

    Optional<Novel> findByIdAndDeletedAtIsNull(Long novelId);
}