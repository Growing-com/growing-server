package org.sarangchurch.growing.term.application.service;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.term.application.dto.UpdateTermRequest;
import org.sarangchurch.growing.term.domain.term.Term;
import org.sarangchurch.growing.term.domain.term.TermEditor;
import org.sarangchurch.growing.term.domain.term.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateTermService {
    private final TermRepository termRepository;

    public void update(Long termId, UpdateTermRequest request) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 텀입니다."));

        if (term.isClosed()) {
            throw new IllegalStateException("종료된 텀의 정보는 수정할 수 없습니다.");
        }

        TermEditor.TermEditorBuilder editorBuilder = term.toEditor();

        TermEditor editor = editorBuilder
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .memo(request.getMemo())
                .groupings(request.getGroupings())
                .build();

        term.edit(editor);
    }
}
