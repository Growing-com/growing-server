package org.sarangchurch.growing.v1.feat.newfamily.application.lineout;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.infra.component.lineinout.NewFamilyLineOutManager;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.attendance.NewFamilyAttendanceUpstream;
import org.sarangchurch.growing.v1.feat.newfamily.infra.stream.user.UserUpstream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LineOutService {
    private final NewFamilyLineOutManager lineOutManager;
    private final UserUpstream userUpstream;
    private final NewFamilyAttendanceUpstream newFamilyAttendanceUpstream;

    @Transactional
    public void lineOut(LineOutRequest request) {
        List<Long> newFamilyIds = request.getNewFamilyIds();

        List<LineOutNewFamily> lineOutNewFamilies = lineOutManager.lineOut(newFamilyIds);

        List<Long> userIds = lineOutNewFamilies.stream()
                .map(LineOutNewFamily::getUserId)
                .collect(Collectors.toList());

        userUpstream.deactivateByIdIn(userIds);
        newFamilyAttendanceUpstream.deleteByNewFamilyIdIn(newFamilyIds);
    }
}
