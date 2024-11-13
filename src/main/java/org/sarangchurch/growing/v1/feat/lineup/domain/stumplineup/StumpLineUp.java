package org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.config.LongArrayListConverter;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.user.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "stump_line_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class StumpLineUp extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "senior_pastor_user_id", nullable = false)
    private Long seniorPastorUserId;

    @Convert(converter = LongArrayListConverter.class)
    @Column(name = "junior_pastor_user_ids", columnDefinition = "json", nullable = false)
    private List<Long> juniorPastorUserIds = new ArrayList<>();

    @Convert(converter = LongArrayListConverter.class)
    @Column(name = "cody_user_ids", columnDefinition = "json", nullable = false)
    private List<Long> codyUserIds = new ArrayList<>();

    @Builder
    public StumpLineUp(Long termId) {
        this.termId = termId;
    }

    public void changeSeniorPastor(Long userId) {
        seniorPastorUserId = userId;
    }

    public void setJuniorPastors(List<User> users) {
        juniorPastorUserIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public void setCodies(List<User> users) {
        codyUserIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

    public boolean containsCody(User cody) {
        return codyUserIds.contains(cody.getId());
    }

    public void removeByUserIds(List<Long> userIds) {
        if (userIds.contains(seniorPastorUserId)) {
            seniorPastorUserId = null;
        }

        codyUserIds.removeAll(userIds);
        juniorPastorUserIds.removeAll(userIds);
    }
}
