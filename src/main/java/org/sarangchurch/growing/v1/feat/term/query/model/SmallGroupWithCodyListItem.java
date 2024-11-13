package org.sarangchurch.growing.v1.feat.term.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class SmallGroupWithCodyListItem {
    private final String codyName;
    private final List<SmallGroupListItem> smallGroupLeaders;

    public SmallGroupWithCodyListItem(List<SmallGroupListItem> smallGroupListItems) {
        this.codyName = smallGroupListItems.get(0).getCodyName();
        this.smallGroupLeaders = smallGroupListItems;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SmallGroupListItem {
        @JsonIgnore
        private final Long codyUserId;

        private final String codyName;
        private final Long smallGroupId;
        private final String smallGroupLeaderName;
    }
}
