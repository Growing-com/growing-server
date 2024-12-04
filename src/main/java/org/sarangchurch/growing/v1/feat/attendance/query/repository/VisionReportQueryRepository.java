package org.sarangchurch.growing.v1.feat.attendance.query.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.attendance.query.model.VisionReport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static org.sarangchurch.growing.v1.feat.attendance.domain.visionreport.QVisionReport.visionReport;

@Repository
@RequiredArgsConstructor
public class VisionReportQueryRepository {
    private final JPAQueryFactory queryFactory;

    public VisionReport getByDate(LocalDate date) {
        return queryFactory.select(Projections.constructor(VisionReport.class,
                        visionReport.active.as("active"),
                        visionReport.registered.as("registered"),
                        visionReport.totalAttend.as("totalAttend"),
                        visionReport.newFamily.as("newFamily"),
                        visionReport.newFamilyAttend.as("newFamilyAttend")
                ))
                .from(visionReport)
                .where(visionReport.date.eq(date))
                .fetchOne();
    }
}
