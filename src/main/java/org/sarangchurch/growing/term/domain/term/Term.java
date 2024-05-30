package org.sarangchurch.growing.term.domain.term;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sarangchurch.growing.core.types.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Term extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private TermStatus status;
    private Boolean isActive;
    private String memo;
    @Column(columnDefinition = "json")
    private String groupings;

    @Builder
    public Term(String name, LocalDate startDate, LocalDate endDate, TermStatus status, Boolean isActive, String memo, String groupings) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.isActive = isActive;
        this.memo = memo;
        this.groupings = groupings;
    }

    public void start() {
        if (status != TermStatus.PENDING) {
            throw new IllegalArgumentException("대기 상태에서만 시작할 수 있습니다.");
        }

        isActive = true;
        status = TermStatus.ACTIVE;
    }

    public void close() {
        if (status != TermStatus.ACTIVE) {
            throw new IllegalArgumentException("진행 상태에서만 종료할 수 있습니다.");
        }

        isActive = false;
        status = TermStatus.CLOSED;
    }

    public boolean isPending() {
        return status == TermStatus.PENDING;
    }

    public TermEditor.TermEditorBuilder toEditor() {
        return TermEditor.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .memo(memo)
                .groupings(groupings);
    }

    public void edit(TermEditor editor) {
        this.name = editor.getName();
        this.startDate = editor.getStartDate();
        this.endDate = editor.getEndDate();
        this.memo = editor.getMemo();
        this.groupings = editor.getGroupings();
    }

    public boolean isClosed() {
        return status == TermStatus.CLOSED;
    }
}
