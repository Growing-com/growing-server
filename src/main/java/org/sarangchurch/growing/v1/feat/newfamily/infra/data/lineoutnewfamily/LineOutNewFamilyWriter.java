package org.sarangchurch.growing.v1.feat.newfamily.infra.data.lineoutnewfamily;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamily;
import org.sarangchurch.growing.v1.feat.newfamily.domain.lineoutnewfamily.LineOutNewFamilyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LineOutNewFamilyWriter {
    private final LineOutNewFamilyRepository lineoutNewFamilyRepository;

    public void deleteById(Long id) {
        lineoutNewFamilyRepository.deleteById(id);
    }

    public List<LineOutNewFamily> saveAll(List<LineOutNewFamily> lineOutNewFamilies) {
        return lineoutNewFamilyRepository.saveAll(lineOutNewFamilies);
    }
}
