package org.sarangchurch.growing.v1.feat.term.infra.data.pastor;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.Pastor;
import org.sarangchurch.growing.v1.feat.term.domain.pastor.PastorRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PastorWriter {
    private final PastorRepository pastorRepository;

    public Pastor save(Pastor pastor) {
        return pastorRepository.save(pastor);
    }

    public void deleteById(Long id) {
        pastorRepository.deleteById(id);
    }

    public void saveAll(List<Pastor> juniorPastors) {
        pastorRepository.saveAll(juniorPastors);
    }
}
