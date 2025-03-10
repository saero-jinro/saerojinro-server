package goorm.saerojinro.api.questions.presentation;

import goorm.saerojinro.api.questions.application.QuestionsFacade;
import goorm.saerojinro.api.questions.presentation.request.QuestionsCreateRequest;
import goorm.saerojinro.api.questions.presentation.request.QuestionsUpdateRequest;
import goorm.saerojinro.api.questions.presentation.response.QuestionsCreateResponse;
import goorm.saerojinro.api.questions.presentation.response.QuestionsListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionsControllerImpl implements QuestionsController{
    private final QuestionsFacade questionsFacade;

    @Override
    @GetMapping
    public ResponseEntity<QuestionsListResponse> getAllQuestions() {
        QuestionsListResponse response = questionsFacade.getAll();
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @GetMapping("/{lectureId}")
    public ResponseEntity<QuestionsListResponse> getByLecture(@PathVariable("lectureId") Long lectureId) {
        QuestionsListResponse response = questionsFacade.getByLecture(lectureId);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @PostMapping("/{lectureId}")
    public ResponseEntity<QuestionsCreateResponse> create(@PathVariable("lectureId") Long lectureId,
                                                          QuestionsCreateRequest request) {
        QuestionsCreateResponse response = questionsFacade.create(lectureId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @PatchMapping("/{lectureId}/{questionsId}")
    public ResponseEntity<Void> update(@PathVariable("questionsId") Long questionsId,
                                       QuestionsUpdateRequest request) {
        questionsFacade.update(questionsId, request);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{lectureId]/{questionsId}")
    public ResponseEntity<Void> delete(@PathVariable("questionsId") Long questionsId) {
        questionsFacade.delete(questionsId);
        return ResponseEntity.noContent().build();
    }
}
