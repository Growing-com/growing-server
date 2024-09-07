package org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.types.BaseEntity;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtcConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "line_out_new_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class LineOutNewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "line_out_date", nullable = false)
    private LocalDate lineOutDate;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Convert(converter = NewFamilyEtcConverter.class)
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private NewFamilyEtc etc;

    public static LineOutNewFamily from(NewFamily newFamily) {
        return new LineOutNewFamily(
                newFamily.getUserId(),
                newFamily.getVisitDate(),
                LocalDate.now(),
                newFamily.getNewFamilyGroupId(),
                newFamily.getEtc()
        );
    }

    private LineOutNewFamily(Long userId, LocalDate visitDate, LocalDate lineOutDate, Long newFamilyGroupId, NewFamilyEtc etc) {
        this.userId = userId;
        this.visitDate = visitDate;
        this.lineOutDate = lineOutDate;
        this.newFamilyGroupId = newFamilyGroupId;
        this.etc = etc;
    }
}
