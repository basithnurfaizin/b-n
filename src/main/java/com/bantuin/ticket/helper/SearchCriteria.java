package com.bantuin.ticket.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SearchCriteria {

    public SearchCriteria(String key, String operation, Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    private String key;
    private String operation;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
