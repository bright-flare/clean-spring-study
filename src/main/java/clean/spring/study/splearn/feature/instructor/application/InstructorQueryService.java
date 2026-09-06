package clean.spring.study.splearn.feature.instructor.application;

import clean.spring.study.splearn.feature.instructor.application.provided.InstructorFinder;
import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class InstructorQueryService implements InstructorFinder {

    private final InstructorRepository repository;

    @Override
    public Instructor findById(Long instructorId) {
        return repository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("강사를 찾을 수 없습니다. ID: " + instructorId));
    }

    @Override
    public Optional<Instructor> findByMember(Long memberId) {
        return repository.findById(memberId);
    }

}
