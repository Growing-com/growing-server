package org.sarangchurch.growing.v1.feat.newfamily.domain.newfamily;

import lombok.Getter;
import org.sarangchurch.growing.core.interfaces.common.BooleanOption;

@Getter
public class NewFamilyEtc {
    private String school;
    private String introducer;
    private BooleanOption isFirstChurch;
    private String latestChurch;
    private NewFamilyVisitReason visitReason;
    private NewFamilyRelationshipWithJesus relationshipWithJesus;
    private BooleanOption hasCertaintityOfSalvation;
    private String comment;
    private String lineUpMemo;
}
