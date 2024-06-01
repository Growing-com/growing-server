package org.sarangchurch.growing.v2.term.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "small_group_leader")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroupLeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id")
    private Long termId;

    @Column(name = "user_id")
    private Long userId;
}
