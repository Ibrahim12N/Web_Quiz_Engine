package engine.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public class QuizInputAnswers {

    private final int[] answer;

    @JsonCreator
    public QuizInputAnswers(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }
    public boolean isEmpty() {
        return answer.length == 0;
    }
}
