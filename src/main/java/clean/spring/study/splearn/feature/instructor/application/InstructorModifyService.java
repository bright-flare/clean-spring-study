package clean.spring.study.splearn.feature.instructor.application;

import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class InstructorModifyService {

    private final InstructorRepository instructorRepository;

}
