package com.ian.novelia.novel.search;

import com.ian.novelia.novel.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NovelSearchCondition {

    private NovelSearchType searchType;
    private String keyword;
    private Category category;

    public String getSearchTypeOrNull() {
        return searchType == null ? null : searchType.name();
    }

    public String getKeywordOrNull() {
        return keyword == null ? null : keyword;
    }

    public Category getCategoryOrNull() {
        return category == null ? null : category;
    }
}
