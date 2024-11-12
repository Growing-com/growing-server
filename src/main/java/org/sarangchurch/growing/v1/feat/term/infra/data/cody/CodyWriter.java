package org.sarangchurch.growing.v1.feat.term.infra.data.cody;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.term.domain.cody.Cody;
import org.sarangchurch.growing.v1.feat.term.domain.cody.CodyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CodyWriter {
    private final CodyRepository codyRepository;

    public void save(Cody cody) {
        codyRepository.save(cody);
    }

    public void delete(Cody cody) {
        codyRepository.deleteById(cody.getId());
    }

    public List<Cody> saveAll(List<Cody> codies) {
        return codyRepository.saveAll(codies);
    }
}
