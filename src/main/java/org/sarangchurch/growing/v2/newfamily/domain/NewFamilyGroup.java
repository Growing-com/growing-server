package org.sarangchurch.growing.v2.newfamily.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;

@Entity(name = "new_family_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewFamilyGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_id", nullable = false)
    private Long termId;
}
