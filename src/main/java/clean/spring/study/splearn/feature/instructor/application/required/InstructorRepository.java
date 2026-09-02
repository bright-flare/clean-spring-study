package clean.spring.study.splearn.feature.instructor.application.required;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;

import java.util.Optional;

public interface InstructorRepository {

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(Long id);

}
