package mock.repository;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.domain.QuestionRepository;
import goorm.saerojinro.domain.question.exception.QuestionNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeQuestionRepository implements QuestionRepository {
    private List<Question> data = Collections.synchronizedList(new ArrayList<>());
    private AtomicLong sequence = new AtomicLong(1);

    @Override
    public List<Question> findAll() {
        return data.stream()
                .toList();
    }

    @Override
    public List<Question> findByLectureId(Long lectureId) {
        return data.stream()
                .filter( q -> q.getLecture().getId().equals(lectureId))
                .toList();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(data.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElseThrow(QuestionNotFoundException::new));
    }

    @Override
    public Question save(Question questions) {

        Question newQuestions = Question.builder()
                .id(sequence.getAndIncrement())
                .user(questions.getUser())
                .lecture(questions.getLecture())
                .content(questions.getContent())
                .build();

        data.add(newQuestions);
        return newQuestions;
    }

    @Override
    public void delete(Question questions) {
        data.remove(questions);
    }
}
