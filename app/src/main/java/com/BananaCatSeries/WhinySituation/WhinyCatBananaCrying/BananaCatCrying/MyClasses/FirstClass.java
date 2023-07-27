package com.BananaCatSeries.WhinySituation.WhinyCatBananaCrying.BananaCatCrying.MyClasses;

public class FirstClass {
    private String Question;
    private String Answer1;
    private String Answer2;
    private int Iv1;
    private int Iv2;

    public FirstClass(String question, String answer1, String answer2, int iv1, int iv2) {
        Question = question;
        Answer1 = answer1;
        Answer2 = answer2;
        Iv1 = iv1;
        Iv2 = iv2;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public int getIv1() {
        return Iv1;
    }

    public void setIv1(int iv1) {
        Iv1 = iv1;
    }

    public int getIv2() {
        return Iv2;
    }

    public void setIv2(int iv2) {
        Iv2 = iv2;
    }

}
