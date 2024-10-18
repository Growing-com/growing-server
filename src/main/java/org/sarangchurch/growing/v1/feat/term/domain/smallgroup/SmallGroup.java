package org.sarangchurch.growing.v1.feat.term.domain.smallgroup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import javax.persistence.*;

@Entity
@Table(name = "small_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO: DB UNIQUE(codyId, smallGroupLeaderId)
public class SmallGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Column(name = "small_group_leader_id", nullable = false)
    private Long smallGroupLeaderId;

    @Builder
    public SmallGroup(Long termId, Long codyId, Long smallGroupLeaderId) {
        this.termId = termId;
        this.codyId = codyId;
        this.smallGroupLeaderId = smallGroupLeaderId;
    }

    public boolean isSameTerm(Term term) {
        return termId.equals(term.getId());
    }
}
