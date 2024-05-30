package org.sarangchurch.growing.__doc__;

import org.springframework.restdocs.payload.FieldDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public interface DocUtils {
    static List<FieldDescriptor> getPagingFieldDescriptors(FieldDescriptor... contentDescriptors) {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();

        Collections.addAll(fieldDescriptors, contentDescriptors);

        fieldDescriptors.add(fieldWithPath("totalElements").description("총 개수"));
        fieldDescriptors.add(fieldWithPath("size").description("페이지 개수"));
        fieldDescriptors.add(fieldWithPath("numberOfElements").description("조회된 개수"));

        fieldDescriptors.add(fieldWithPath("totalPages").description("총 페이지 수"));
        fieldDescriptors.add(fieldWithPath("first").description("첫번째 페이지 여부"));
        fieldDescriptors.add(fieldWithPath("last").description("마지막 페이지 여부"));

        fieldDescriptors.add(fieldWithPath("pageable").ignored());
        fieldDescriptors.add(fieldWithPath("empty").ignored());
        fieldDescriptors.add(fieldWithPath("number").ignored());
        fieldDescriptors.add(fieldWithPath("sort.unsorted").ignored());
        fieldDescriptors.add(fieldWithPath("sort.sorted").ignored());
        fieldDescriptors.add(fieldWithPath("sort.empty").ignored());

        return fieldDescriptors;
    }
}
