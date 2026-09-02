package clean.spring.study.splearn.feature.instructor.adapter.persistence;

import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InstructorRepositoryImpl implements InstructorRepository {

    private final InstructorJpaRepository jpaRepository;

    @Override
    public Instructor save(Instructor instructor) {
        return jpaRepository.save(instructor);
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        return jpaRepository.findById(id);
    }

}
