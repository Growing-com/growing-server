package org.sarangchurch.growing.training.application.training.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.training.dto.UpdateTrainingRequest;
import org.sarangchurch.growing.training.domain.training.Training;
import org.sarangchurch.growing.training.domain.training.TrainingEditor;
import org.sarangchurch.growing.training.domain.training.TrainingRepository;
import org.sarangchurch.growing.training.infrastructure.UserDownsStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateTrainingService {

    private final TrainingRepository trainingRepository;
    private final UserDownsStream userDownsStream;

    public void update(Long trainingId, UpdateTrainingRequest request) {
        userDownsStream.validateIds(request.getUserIds());

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("훈련이 존재하지 않습니다."));

        TrainingEditor.TrainingEditorBuilder editorBuilder = training.toEditor();

        TrainingEditor editor = editorBuilder
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .userIds(request.getUserIds())
                .etc(request.getEtc())
                .build();

        training.edit(editor);
    }
}
