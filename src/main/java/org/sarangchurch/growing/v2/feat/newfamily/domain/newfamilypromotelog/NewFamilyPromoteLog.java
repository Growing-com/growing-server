package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamilypromotelog;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "new_family_promote_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyPromoteLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 필요 없을듯
    @Column(name = "new_family_id", nullable = false)
    private Long newFamilyId;

    @Column(name = "promote_data")
    private LocalDate promoteDate;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    public static List<NewFamilyPromoteLog> ofSize(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> NewFamilyPromoteLog.builder().build())
                .collect(Collectors.toList());
    }

    @Builder
    private NewFamilyPromoteLog(Long newFamilyId, LocalDate promoteDate, Long smallGroupId) {
        this.newFamilyId = newFamilyId;
        this.promoteDate = promoteDate;
        this.smallGroupId = smallGroupId;
    }

    public void updateSmallGroupId(Long smallGroupId) {
        this.smallGroupId = smallGroupId;
    }
}
