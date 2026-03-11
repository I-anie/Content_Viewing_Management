package com.ian.novelia.episode.domain;

import com.ian.novelia.global.base.BaseEntity;
import com.ian.novelia.novel.domain.Novel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "episodes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Episode extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private Integer price = 100;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "novel_id", nullable = false)
    private Novel novel;

    public void updateEpisode(String title, String content) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
    }
}
