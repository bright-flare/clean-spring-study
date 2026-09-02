package clean.spring.study.splearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "clean.spring.study.splearn", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

  /**
   * 레이어가 최상위가 아닌 feature 하위에 있으므로(feature.member.domain, feature.instructor.domain, ...)
   * 레이어 정의에 feature 이름 자리를 와일드카드로 둔다.
   */
  @ArchTest
  void hexagonalArchitecture(JavaClasses classes) {

    Architectures.layeredArchitecture()
            // 필드 타입, 제네릭 타입 인자, 애노테이션까지 포함해 검사한다 (기본값은 직접 호출만)
            .consideringAllDependencies()
            // ArchUnit의 '*'는 패키지 한 세그먼트(= feature 이름)만 매칭한다. '..'와 달리 feature 경계를 넘지 않는다
            .layer("domain").definedBy("clean.spring.study.splearn.feature.*.domain..")
            .layer("application").definedBy("clean.spring.study.splearn.feature.*.application..")
            .layer("adapter").definedBy("clean.spring.study.splearn.feature.*.adapter..")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
            .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
            .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
            // withOptionalLayers()를 쓰지 않는다. 빈 레이어가 실패로 잡혀야 패키지 구조가 바뀐 걸 이 테스트가 알려준다
            .check(classes);
  }
  
}
