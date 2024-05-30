package org.sarangchurch.growing.training.application.training.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.training.dto.CreateTrainingRequest;
import org.sarangchurch.growing.training.domain.training.Training;
import org.sarangchurch.growing.training.domain.training.TrainingRepository;
import org.sarangchurch.growing.training.infrastructure.UserDownsStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTrainingService {

    private final TrainingRepository trainingRepository;
    private final UserDownsStream userDownsStream;

    public void create(CreateTrainingRequest request) {
        validateDate(request);

        userDownsStream.validateIds(request.getUserIds());

        Training training = Training.builder()
                .name(request.getName())
                .type(request.getType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .etc(request.getEtc())
                .build();

        training.setMembers(request.getUserIds());

        trainingRepository.save(training);
    }

    private void validateDate(CreateTrainingRequest request) {
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
