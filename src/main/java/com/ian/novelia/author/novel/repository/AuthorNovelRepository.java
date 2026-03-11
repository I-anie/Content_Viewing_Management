package com.ian.novelia.author.novel.repository;

import com.ian.novelia.novel.domain.Category;
import com.ian.novelia.novel.domain.Novel;
import com.ian.novelia.auth.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorNovelRepository extends JpaRepository<Novel, Long> {

    @Query("""
                select n
                from Novel n
                join n.user u
                where u = :user
                    and (:category is null or n.category = :category)
                    and (
                        :keyword is null or :keyword = ''
                        or (:searchType = 'TITLE' and n.title like concat('%', :keyword, '%'))
                        or (:searchType = 'DESCRIPTION' and n.description like concat('%', :keyword, '%'))
                        or (:searchType = 'AUTHOR' and u.penName like concat('%', :keyword, '%'))
                    )
                    and n.deletedAt is null
            """)
    Page<Novel> searchNovels(
            @Param("user") User user,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("category") Category category,
            Pageable pageable
    );

    Optional<Novel> findByIdAndUserUsernameAndDeletedAtIsNull(Long novelId, String username);
}
