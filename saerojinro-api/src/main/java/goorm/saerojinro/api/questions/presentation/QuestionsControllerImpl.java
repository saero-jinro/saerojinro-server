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
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       QuestionsUpdateRequest request) {
        questionsFacade.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        questionsFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
