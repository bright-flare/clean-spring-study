package clean.spring.study.splearn.application.instructor.required;

import clean.spring.study.splearn.domain.instructor.Instructor;

import java.util.Optional;

public interface InstructorRepository {

    Instructor save(Instructor instructor);

    Optional<Instructor> findById(Long id);

}
