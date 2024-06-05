package org.sarangchurch.growing.v2.newfamily.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "new_family_promote_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyPromoteLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promote_data", nullable = false)
    private LocalDate promoteDate;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Builder
    private NewFamilyPromoteLog(LocalDate promoteDate, Long smallGroupId) {
        this.promoteDate = promoteDate;
        this.smallGroupId = smallGroupId;
    }
}
