package org.sarangchurch.growing.v1.feat.term.domain.pastor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;
import org.sarangchurch.growing.v1.feat.term.domain.term.Term;

import javax.persistence.*;

@Entity
@Table(name = "pastor")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pastor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "is_senior", nullable = false)
    private boolean isSenior;

    @Builder
    public Pastor(Long termId, Long userId, boolean isSenior) {
        this.termId = termId;
        this.userId = userId;
        this.isSenior = isSenior;
    }

    public void toJunior() {
        isSenior = false;
    }

    public void toSenior() {
        isSenior = true;
    }

    public boolean isSameTerm(Pastor anotherPastor) {
        return this.getTermId().equals(anotherPastor.getTermId());
    }

    public boolean isSameTerm(Term term) {
        return this.getTermId().equals(term.getId());
    }
}
