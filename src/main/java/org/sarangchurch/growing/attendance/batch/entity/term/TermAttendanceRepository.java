package org.sarangchurch.growing.attendance.batch.entity.term;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TermAttendanceRepository extends JpaRepository<TermAttendance, Long> {
    Optional<TermAttendance> findByTermId(Long termId);
    void deleteByTermId(Long termId);
}
