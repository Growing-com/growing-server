package org.sarangchurch.growing.v1.feat.user.domain.lineoutuser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "line_out_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineOutUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "line_out_date", nullable = false)
    private LocalDate lineOutDate;

    @Column(name = "reason")
    private String reason;
}
