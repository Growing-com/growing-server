package org.sarangchurch.growing.v1.feat.term.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class SmallGroupListItem {
    private final String codyName;
    private final List<SmallGroupListItemLeaders> smallGroupLeaders;

    public SmallGroupListItem(List<SmallGroupListItemLeaders> smallGroupLeaders) {
        this.codyName = smallGroupLeaders.get(0).getCodyName();
        this.smallGroupLeaders = smallGroupLeaders;
    }

    @Getter
    @RequiredArgsConstructor
    public static class SmallGroupListItemLeaders {
        @JsonIgnore
        private final Long codyUserId;

        private final String codyName;
        private final Long smallGroupId;
        private final String smallGroupLeaderName;
    }
}
