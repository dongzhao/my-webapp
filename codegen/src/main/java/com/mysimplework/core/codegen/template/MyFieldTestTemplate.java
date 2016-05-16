package com.mysimplework.core.codegen.template;

/**
 * Created by dzhao on 26/11/2015.
 */
public class MyFieldTestTemplate extends MyFieldTemplate{

    private String inputValue;
    private String outputValue;
    private String expectedValue;

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(String expectedValue) {
        this.expectedValue = expectedValue;
    }
}
