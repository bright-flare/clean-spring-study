package clean.spring.study.splearn.feature.instructor.application;

import clean.spring.study.splearn.feature.instructor.application.dto.InstructorApplyRequest;
import clean.spring.study.splearn.feature.instructor.application.provided.DuplicateInstructorApplicationException;
import clean.spring.study.splearn.feature.instructor.application.provided.InstructorApplication;
import clean.spring.study.splearn.feature.instructor.application.provided.InstructorFinder;
import clean.spring.study.splearn.feature.instructor.application.required.InstructorRepository;
import clean.spring.study.splearn.feature.instructor.domain.Instructor;
import clean.spring.study.splearn.feature.member.application.provided.MemberFinder;
import clean.spring.study.splearn.feature.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class InstructorModifyService implements InstructorApplication {

    private final InstructorRepository instructorRepository;
    private final InstructorFinder instructorFinder;
    private final MemberFinder memberFinder;

    @Override
    public Instructor apply(InstructorApplyRequest request) {

        Member member = memberFinder.find(request.memberId());

        checkDuplicateApplication(member);

        Instructor instructor = Instructor.apply(member);

        return instructorRepository.save(instructor);
    }

    private void checkDuplicateApplication(Member member) {
        if (instructorRepository.findById(member.getId()).isPresent()) {
            throw new DuplicateInstructorApplicationException();
        }
    }

    @Override
    public Instructor approve(Long instructorId) {

        Instructor instructor = instructorFinder.findById(instructorId);

        instructor.approve();

        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor reject(Long instructorId) {
        Instructor instructor = instructorFinder.findById(instructorId);

        instructor.reject();

        return instructorRepository.save(instructor);
    }

}
