package goorm.saerojinro.api.question.presentation;

import goorm.saerojinro.api.question.application.QuestionFacade;
import goorm.saerojinro.api.question.presentation.request.QuestionCreateRequest;
import goorm.saerojinro.api.question.presentation.request.QuestionUpdateRequest;
import goorm.saerojinro.api.question.presentation.response.QuestionCreateResponse;
import goorm.saerojinro.api.question.presentation.response.QuestionListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionControllerImpl implements QuestionController {
    private final QuestionFacade questionsFacade;

    @Override
    @GetMapping
    public ResponseEntity<QuestionListResponse> getAllQuestions() {
        QuestionListResponse response = questionsFacade.getAll();
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @GetMapping("/{lectureId}")
    public ResponseEntity<QuestionListResponse> getByLecture(@PathVariable("lectureId") Long lectureId) {
        QuestionListResponse response = questionsFacade.getByLecture(lectureId);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @PostMapping("/{lectureId}")
    public ResponseEntity<QuestionCreateResponse> create(@PathVariable("lectureId") Long lectureId,
                                                         QuestionCreateRequest request) {
        QuestionCreateResponse response = questionsFacade.create(lectureId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       QuestionUpdateRequest request) {
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
