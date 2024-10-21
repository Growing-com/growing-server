package org.sarangchurch.growing.core.interfaces.v1.term;

public interface SmallGroupMemberService {
    void create(Long userId, Long smallGroupId);

    void update(Long userId, Long targetSmallGroupId);
}
