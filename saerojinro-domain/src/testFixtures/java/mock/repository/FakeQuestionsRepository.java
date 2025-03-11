package mock.repository;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.domain.QuestionsRepository;
import goorm.saerojinro.domain.questions.exception.QuestionsNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeQuestionsRepository implements QuestionsRepository {
    private List<Questions> data = Collections.synchronizedList(new ArrayList<>());
    private AtomicLong sequence = new AtomicLong(1);

    @Override
    public List<Questions> findAll() {
        return data.stream()
                .toList();
    }

    @Override
    public List<Questions> findByLecture(Lecture lecture) {
        return data.stream()
                .filter( q -> q.getLecture().getId().equals(lecture.getId()))
                .toList();
    }

    @Override
    public Optional<Questions> findById(Long id) {
        return Optional.ofNullable(data.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElseThrow(QuestionsNotFoundException::new));
    }

    @Override
    public Questions save(Questions questions) {

        Questions newQuestions = Questions.builder()
                .id(sequence.getAndIncrement())
                .user(questions.getUser())
                .lecture(questions.getLecture())
                .content(questions.getContent())
                .build();

        data.add(newQuestions);
        return newQuestions;
    }

    @Override
    public void delete(Questions questions) {
        data.remove(questions);
    }
}
