package clean.spring.study.splearn.feature.instructor.adapter.persistence;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorJpaRepository extends JpaRepository<Instructor, Long> {
}
