package org.sarangchurch.growing.v2.feat.newfamily.domain.lineoutnewfamily;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.sarangchurch.growing.core.types.BaseEntity;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily.NewFamilyEtc;
import org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily.NewFamilyEtcConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "line_out_new_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@TypeDef(name = "json", typeClass = JsonType.class)
public class LineoutNewFamily extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @Column(name = "new_family_group_id")
    private Long newFamilyGroupId;

    @Convert(converter = NewFamilyEtcConverter.class)
    @Column(name = "etc", columnDefinition = "json", nullable = false)
    private NewFamilyEtc etc;

    public static LineoutNewFamily from(NewFamily newFamily) {
        return new LineoutNewFamily(
                newFamily.getUserId(),
                newFamily.getVisitDate(),
                newFamily.getNewFamilyGroupId(),
                newFamily.getEtc()
        );
    }

    private LineoutNewFamily(Long userId, LocalDate visitDate, Long newFamilyGroupId, NewFamilyEtc etc) {
        this.userId = userId;
        this.visitDate = visitDate;
        this.newFamilyGroupId = newFamilyGroupId;
        this.etc = etc;
    }
}
