package com.mysimplework.core.enums;

/**
 * Created by dzhao on 14/10/2015.
 */
public enum OperatorEnum {
    EQUALS("Equals"),
    AND("And"),
    OR("Or"),
    AFTER("After"),
    BEFORE("Before"),
    LIKE("Like"),
    IN("In"),
    NOT("Not");

    private String value;

    OperatorEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public OperatorEnum valueFrom(String value){
        for (OperatorEnum operator : OperatorEnum.values()){
            if( operator.value.equalsIgnoreCase(value) ){
                return operator;
            }
        }
        throw new UnsupportedOperationException("unsupported enum type of name ["+ value +"]");
    }
}
