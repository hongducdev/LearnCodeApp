package com.example.learncodeapp;

public class QuestionModel {
  String question;
  String opt1;
  String opt2;
  String opt3;
  String opt4;
  int correctAnswer;

  public QuestionModel() {
  }

  public QuestionModel(String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
    this.question = question;
    this.opt1 = answer1;
    this.opt2 = answer2;
    this.opt3 = answer3;
    this.opt4 = answer4;
    this.correctAnswer = correctAnswer;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getOption1() {
    return opt1;
  }

  public void setOption1(String answer1) {
    this.opt1 = answer1;
  }

  public String getOption2() {
    return opt2;
  }

  public void setOption2(String answer2) {
    this.opt2 = answer2;
  }

  public String getOption3() {
    return opt3;
  }

  public void setOption3(String answer3) {
    this.opt3 = answer3;
  }

  public String getOption4() {
    return opt4;
  }

  public void setOption4(String answer4) {
    this.opt4 = answer4;
  }

  public int getCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(int correctAnswer) {
    this.correctAnswer = correctAnswer;
  }
}
