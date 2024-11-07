package org.sarangchurch.growing.v1.feat.lineup.domain.smallgroupmemberlineup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "small_group_member_line_up")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGroupMemberLineUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;

    @Column(name = "small_group_leader_line_up_id", nullable = false)
    private Long smallGroupLeaderLineUpId;

    @Column(name = "member_user_id", nullable = false)
    private Long memberUserId;
}
