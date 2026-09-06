package clean.spring.study.splearn.feature.course.domain;

import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Course {

    @Id
    private Long id;
    private String title;
    @ManyToOne
    private Instructor instructor;
    private CourseStatus status;
    @Embedded
    private CourseDetail detail;

}
