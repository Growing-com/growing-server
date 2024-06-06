package org.sarangchurch.growing.v2.term.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sarangchurch.growing.v2.core.interfaces.term.TermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TermServiceImpl implements TermService {
    @Override
    public void lineupUser(Long userId, Long smallGroupId) {
        log.info("line up user={} to smallGroup={}", userId, smallGroupId);
    }
}
