package clean.spring.study.splearn.support.stereotype;

import clean.spring.study.splearn.config.SplearnTestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public @interface ApplicationServiceTest {
}
