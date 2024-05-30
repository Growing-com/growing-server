package org.sarangchurch.growing.training.application.discipleship.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.training.application.discipleship.dto.UpdateDiscipleshipRequest;
import org.sarangchurch.growing.training.domain.discipleship.Discipleship;
import org.sarangchurch.growing.training.domain.discipleship.DiscipleshipEditor;
import org.sarangchurch.growing.training.domain.discipleship.DiscipleshipRepository;
import org.sarangchurch.growing.training.infrastructure.UserDownsStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateDiscipleshipService {

    private final DiscipleshipRepository discipleshipRepository;
    private final UserDownsStream userDownsStream;

    public void update(Long trainingId, UpdateDiscipleshipRequest request) {
        userDownsStream.validateIds(request.getUserIds());

        Discipleship discipleship = discipleshipRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("훈련이 존재하지 않습니다."));

        DiscipleshipEditor.DiscipleshipEditorBuilder editorBuilder = discipleship.toEditor();

        DiscipleshipEditor editor = editorBuilder
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .userIds(request.getUserIds())
                .etc(request.getEtc())
                .build();

        discipleship.edit(editor);
    }
}
