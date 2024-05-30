package org.sarangchurch.growing.term.infra;

import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistory;
import org.sarangchurch.growing.term.domain.newComerHistory.NewComerHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaNewComerHistoryRepository extends JpaRepository<NewComerHistory, Long>, NewComerHistoryRepository {
}
