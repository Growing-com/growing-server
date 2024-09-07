package org.sarangchurch.growing.v1.feat.attendance.infra.jpa;

import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendance;
import org.sarangchurch.growing.v1.feat.attendance.domain.newfamilyattendance.NewFamilyAttendanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface JpaNewFamilyAttendanceRepository extends JpaRepository<NewFamilyAttendance, Long>, NewFamilyAttendanceRepository {
    @Override
    @Modifying
    @Query("DELETE FROM NewFamilyAttendance nfa WHERE nfa.newFamilyId IN :newFamilyIds AND nfa.date = :date")
    void deleteByNewFamilyIdInAndDate(List<Long> newFamilyIds, LocalDate date);
}
