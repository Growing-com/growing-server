package org.sarangchurch.growing.v2.term.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "small_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id")
    private Long termId;

    // 간사도 나중에 포함해야하나?

    @Column(name = "small_group_leader_id")
    private Long smallGroupLeaderId;
}
