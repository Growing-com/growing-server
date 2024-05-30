package org.sarangchurch.growing.training.application.discipleship.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.discipleship.dto.CreateDiscipleshipRequest;
import org.sarangchurch.growing.training.domain.discipleship.Discipleship;
import org.sarangchurch.growing.training.domain.discipleship.DiscipleshipRepository;
import org.sarangchurch.growing.training.infrastructure.UserDownsStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateDiscipleshipService {

    private final DiscipleshipRepository discipleshipRepository;
    private final UserDownsStream userDownsStream;

    public void create(CreateDiscipleshipRequest request) {
        validateDate(request);

        userDownsStream.validateIds(request.getUserIds());

        Discipleship discipleship = Discipleship.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .etc(request.getEtc())
                .build();

        discipleship.setMembers(request.getUserIds());

        discipleshipRepository.save(discipleship);
    }

    private void validateDate(CreateDiscipleshipRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (endDate == null) {
            return;
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작날짜가 종료날짜보다 미래일 수 없습니다.");
        }
    }
}
