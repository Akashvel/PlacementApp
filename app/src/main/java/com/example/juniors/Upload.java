package com.example.juniors;

public class Upload {
    String topic;
    String quesone;
    String questwo;
    String codingques;
    String word;

    public Upload(){

    }

    public Upload(String topic, String quesone, String questwo, String codingques,String word) {
        this.topic = topic;
        this.quesone = quesone;
        this.questwo = questwo;
        this.codingques = codingques;
        this.word = word;
    }

    public String getTopic() {
        return topic;
    }

    public String getQuesone() {
        return quesone;
    }

    public String getQuestwo() {
        return questwo;
    }

    public String getCodingques() {
        return codingques;
    }

    public String getWord() {
        return word;
    }
}
