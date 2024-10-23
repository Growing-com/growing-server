package org.sarangchurch.growing.v1.feat.term.domain.pastor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

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
}
