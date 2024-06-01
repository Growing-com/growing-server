package org.sarangchurch.growing.v2.term.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "small_group_member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id")
    private Long termId;

    @Column(name = "small_group_id")
    private Long smallGroupId;

    @Column(name = "user_id")
    private Long userId;
}
