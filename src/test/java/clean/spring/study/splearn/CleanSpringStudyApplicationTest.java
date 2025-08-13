package clean.spring.study.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class CleanSpringStudyApplicationTest {

  @Test
  void run() {
    
    try (MockedStatic<SpringApplication> mock = Mockito.mockStatic(SpringApplication.class)) {
      CleanSpringStudyApplication.main(new String[0]);
      
      mock.verify(() -> SpringApplication.run(CleanSpringStudyApplication.class, new String[0]));
    }
    
  }
  
}