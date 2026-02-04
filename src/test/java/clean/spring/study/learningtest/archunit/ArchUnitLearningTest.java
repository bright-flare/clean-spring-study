package clean.spring.study.learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "clean.spring.study.learningtest.archunit")
public class ArchUnitLearningTest {

  /**
   * Application class를 의존하는 class는 application, adapter에만 존재해야 한다.
   */
  
  @ArchTest
  void application(JavaClasses classes) {

    classes().that().resideInAPackage("..application..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
            .check(classes);
  }
  
  /**
   * Application class는 Adapter를 의존하면 안된다.
   */
  @ArchTest
  void adapter(JavaClasses classes) {

    noClasses().that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
            .check(classes);
  }
  
  /**
   * Domain class는 domain, java 기본 패키지만 의존해야한다.
   */
  @ArchTest
  void domain(JavaClasses classes) {

    classes().that().resideInAPackage("..domain..")
            .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "java..")
            .check(classes);
  }
  
}
