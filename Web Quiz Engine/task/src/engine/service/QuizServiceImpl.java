package engine.service;


import engine.dao.CompletedQuizRepository;
import engine.dao.UserRepository;
import engine.entity.*;
import engine.dao.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService{

    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private final HttpServletRequest request;
    @Autowired
    private final CompletedQuizRepository completedQuizRepository;
    @Autowired
    private final UserRepository userRepository;

    public QuizServiceImpl(QuizRepository quizRepository, CompletedQuizRepository completedQuizRepository, HttpServletRequest request, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.completedQuizRepository = completedQuizRepository;
        this.request = request;
        this.userRepository = userRepository;

    }


    public Quiz save(Quiz toSave) {
        return quizRepository.save(toSave);
    }


    public Page<Quiz> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo,pageSize);
        return quizRepository.findAll(paging);
    }

    public Quiz findQuizById(long id){
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (quizOptional.isPresent()) {
            return quizOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "quiz not found"
            );
        }
    }
       public int [] getAnswer(long id){
           Optional<Quiz> quizOptional = quizRepository.findById(id);
           if (quizOptional.isPresent()) {
               return quizOptional.get().getAnswer();
           } else {
               throw new ResponseStatusException(
                       HttpStatus.NOT_FOUND, "quiz not found"
               );
           }
       }
       public void deleteQuiz(Quiz quiz){
        quizRepository.delete(quiz);
       }

        public Page<CompletedQuiz> getAllCompletedQuizzes(Integer page) {
        User user = userRepository.findByEmail(request.getUserPrincipal().getName());
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completedQuizRepository.findAllByUserId(user.getId(), pageable);
    }

    @Override
    public Response solveQuiz(QuizInputAnswers answer, Long id) {
        User user = userRepository.findByEmail(request.getUserPrincipal().getName());
        Quiz quiz = findQuizById(id);
        if (answer.isEmpty() && quiz.isAnswerNull() || Arrays.equals(answer.getAnswer(), quiz.getAnswer())) {
            CompletedQuiz completedQuiz = new CompletedQuiz();
            completedQuiz.setQuizId(quiz.getId());
            completedQuiz.setCompletedAt(LocalDateTime.now());
            completedQuiz.setUser(user);
            completedQuizRepository.save(completedQuiz);
          return new Response(true, "Congratulations, you're right!");
        } else {
            return new Response(false, "Wrong answer! Please, try again.");
        }
    }

}
