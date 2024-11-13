package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamilypromotelog;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.config.data.LongArrayListConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "new_family_promote_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class NewFamilyPromoteLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promote_date")
    private LocalDate promoteDate;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Convert(converter = LongArrayListConverter.class)
    @Column(name = "temporary_small_group_ids", columnDefinition = "json", nullable = false)
    private List<Long> temporarySmallGroupIds = new ArrayList<>();

    public static List<NewFamilyPromoteLog> ofSize(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> NewFamilyPromoteLog.builder().build())
                .collect(Collectors.toList());
    }

    @Builder
    private NewFamilyPromoteLog(LocalDate promoteDate, Long smallGroupId) {
        this.promoteDate = promoteDate;
        this.smallGroupId = smallGroupId;
    }

    public void updateSmallGroupId(Long smallGroupId) {
        this.smallGroupId = smallGroupId;
    }

    public boolean isPromoted() {
        return promoteDate != null;
    }

    public boolean isPromoteCandidate() {
        return smallGroupId != null && promoteDate == null;
    }

    public void updatePromoteDate(LocalDate promoteDate) {
        this.promoteDate = promoteDate;
    }

    public void updateTemporarySmallGroups(List<Long> temporarySmallGroupIds) {
        this.temporarySmallGroupIds = temporarySmallGroupIds;
    }
}
