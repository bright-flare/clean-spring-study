package clean.spring.study.splearn.adapter;

import clean.spring.study.splearn.domain.member.DuplicateEmailException;
import clean.spring.study.splearn.domain.member.DuplicateProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception exception) {
    return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
  }

  /*
  * since 6.0 
  * RFC9457 - 예외에 대한 응답 표준
  * */
  @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
  public ProblemDetail emailExceptionHandler(DuplicateEmailException exception) {
    return getProblemDetail(HttpStatus.CONFLICT, exception);
  }

  private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
    problemDetail.setProperty("exception", exception.getClass().getSimpleName());
    problemDetail.setProperty("timestamp", LocalDateTime.now());

    return problemDetail;
  }

}
