package com.ian.novelia.novel.domain;


import com.ian.novelia.global.base.BaseEntity;
import com.ian.novelia.auth.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "novels")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Novel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    public void updateNovel(String thumbnailUrl, String title, String description, Category category) {
        if (thumbnailUrl != null) this.thumbnailUrl = thumbnailUrl;
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (category != null) this.category = category;
    }
}
