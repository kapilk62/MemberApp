package com.example.memberapp.Model;

public class Rule{
    String ruleId;
    String rule;

    public Rule(){}

    public Rule(String ruleId,String rule) {
        this.ruleId = ruleId;
        this.rule = rule;
    }

    public String getRuleId(){
        return ruleId;
    }

    public String getRule() {
        return rule;
    }

}
