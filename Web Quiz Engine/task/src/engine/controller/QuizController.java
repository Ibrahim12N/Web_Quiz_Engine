package engine.controller;

import engine.entity.*;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("quizzes")
    public Quiz saveQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User user) {
        quiz.setUser(user);
        Quiz theQuiz = quizService.save(quiz);
        return theQuiz;
    }

    @GetMapping("quizzes/{id}")
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.findQuizById(id);
    }

    @GetMapping(path = "/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<Page<Quiz>>(quizService.findAll(page, pageSize), HttpStatus.OK);
    }

    @PostMapping(value = "quizzes/{id}/solve")
    public Response solveQuiz(@RequestBody QuizInputAnswers answer, @PathVariable Long id) {
        return quizService.solveQuiz( answer , id);
    }

    @DeleteMapping("quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Quiz quiz = quizService.findQuizById(id);
        if (quiz.equals(null))
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        else if (quiz.getUser().getId().equals(user.getId())) {
            quizService.deleteQuiz(quiz);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("quizzes/completed")
    public Page<CompletedQuiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getAllCompletedQuizzes(page);
    }
}



