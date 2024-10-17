package org.sarangchurch.growing.v1.feat.user.domain.dispatcheduser;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dispatched_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DispatchedUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DispatchType type;

    @Column(name = "send_date", nullable = false)
    private LocalDate sendDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;
}
