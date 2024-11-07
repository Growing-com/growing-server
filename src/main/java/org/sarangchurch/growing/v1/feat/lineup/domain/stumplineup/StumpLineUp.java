package org.sarangchurch.growing.v1.feat.lineup.domain.stumplineup;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.config.LongArrayListConverter;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
}
