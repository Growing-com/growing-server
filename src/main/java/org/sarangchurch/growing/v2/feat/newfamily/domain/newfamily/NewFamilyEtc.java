package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily;

import lombok.Getter;

@Getter
public class NewFamilyEtc {
    private String school;
    private String comment;
    private String introducer;
    private NewFamilyVisitReason visitReason;
    private String latestChurch;
    private Boolean isFirstChurch;
    private String address;
    private NewFamilyRelationshipWithJesus relationshipWithJesus;
    private Boolean hasCertaintityOfSalvation;
}
