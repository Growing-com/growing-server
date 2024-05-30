package org.sarangchurch.growing.training.domain.discipleship;

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
public class Discipleship extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Embedded
    private DiscipleshipMembers members = new DiscipleshipMembers();
    private String etc;

    @Builder
    public Discipleship(String name, LocalDate startDate, LocalDate endDate, String etc) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.etc = etc;
    }

    public void setMembers(List<Long> userIds) {
        members.set(id, userIds);
    }

    public DiscipleshipEditor.DiscipleshipEditorBuilder toEditor() {
        return DiscipleshipEditor.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .userIds(members.getUserIds())
                .etc(etc);
    }

    public void edit(DiscipleshipEditor editor) {
        this.name = editor.getName();
        this.startDate = editor.getStartDate();
        this.endDate = editor.getEndDate();
        this.etc = editor.getEtc();

        setMembers(editor.getUserIds());
    }
}
