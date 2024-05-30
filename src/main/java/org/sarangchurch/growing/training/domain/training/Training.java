package org.sarangchurch.growing.training.domain.training;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Training extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private TrainingType type;
    private LocalDate startDate;
    private LocalDate endDate;
    @Embedded
    private TrainingMembers members = new TrainingMembers();
    private String etc;

    @Builder
    public Training(String name, TrainingType type, LocalDate startDate, LocalDate endDate, String etc) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.etc = etc;
    }

    public void setMembers(List<Long> userIds) {
        members.set(id, userIds);
    }

    public TrainingEditor.TrainingEditorBuilder toEditor() {
        return TrainingEditor.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .userIds(members.getUserIds())
                .etc(etc);
    }

    public void edit(TrainingEditor editor) {
        this.name = editor.getName();
        this.startDate = editor.getStartDate();
        this.endDate = editor.getEndDate();
        this.etc = editor.getEtc();

        setMembers(editor.getUserIds());
    }
}
