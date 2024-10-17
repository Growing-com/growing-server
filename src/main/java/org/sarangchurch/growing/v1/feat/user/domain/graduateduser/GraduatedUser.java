package org.sarangchurch.growing.v1.feat.user.domain.graduateduser;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "graduated_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraduatedUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "graduate_date", nullable = false)
    private LocalDate graduateDate;

    @Builder
    public GraduatedUser(Long userId, LocalDate graduateDate) {
        this.userId = userId;
        this.graduateDate = graduateDate;
    }
}
