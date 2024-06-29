package org.sarangchurch.growing.v2.feat.newfamily.domain.newfamily;

public enum NewFamilyRelationshipWithJesus {
    LORD("나를 구원해 주신 주님으로 믿고 있다."),
    HESITANT("믿고 싶지만 어떻게 해야 할지 모르겠다"),
    NONE("잘 모른다."),
    NOT_INTERESTED("알고 싶지도 않고 관심도 없다")
    ;

    private final String value;

    NewFamilyRelationshipWithJesus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
