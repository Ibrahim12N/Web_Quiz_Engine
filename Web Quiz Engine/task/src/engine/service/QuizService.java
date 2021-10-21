package engine.service;


import engine.entity.Response;
import engine.entity.CompletedQuiz;
import engine.entity.Quiz;
import engine.entity.QuizInputAnswers;
import org.springframework.data.domain.Page;

public interface QuizService {
    Quiz save(Quiz toSave);
    Page<Quiz> findAll(int pageNo, int pageSize);
    Quiz findQuizById(long id);
    void deleteQuiz(Quiz quiz);
    Page<CompletedQuiz> getAllCompletedQuizzes(Integer page);
    Response solveQuiz(QuizInputAnswers answer, Long id);
}
