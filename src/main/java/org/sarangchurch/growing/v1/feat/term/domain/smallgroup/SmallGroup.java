package org.sarangchurch.growing.v1.feat.term.domain.smallgroup;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import javax.persistence.*;

@Entity
@Table(name = "small_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "cody_id", nullable = false)
    private Long codyId;

    @Column(name = "leader_user_id", nullable = false)
    private Long leaderUserId;

    @Builder
    public SmallGroup(Long termId, Long codyId, Long leaderUserId) {
        this.termId = termId;
        this.codyId = codyId;
        this.leaderUserId = leaderUserId;
    }

    public boolean isSameTerm(Term term) {
        return termId.equals(term.getId());
    }

    public void updateCody(Cody cody) {
        if (!cody.getTermId().equals(termId)) {
            throw new IllegalStateException("코디와 순모임이 속한 텀이 일치하지 않습니다.");
        }

        codyId = cody.getId();
    }
}
