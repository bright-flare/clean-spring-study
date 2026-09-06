package clean.spring.study.splearn;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AnalyzeClasses(packages = "clean.spring.study.splearn", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

  final String basePackage = "clean.spring.study.splearn.";


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
            .layer("domain").definedBy(basePackage + "feature.*.domain..")
            .layer("application").definedBy(basePackage + "feature.*.application..")
            .layer("adapter").definedBy(basePackage + "feature.*.adapter..")
            .whereLayer("domain").mayOnlyBeAccessedByLayers("application", "adapter")
            .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
            .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
            // withOptionalLayers()를 쓰지 않는다. 빈 레이어가 실패로 잡혀야 패키지 구조가 바뀐 걸 이 테스트가 알려준다
            .check(classes);
  }

  @ArchTest
  void domainFreeOfCycles(JavaClasses classes) {

    SlicesRuleDefinition.slices()
            .matching(basePackage + "feature.(*).domain..")
            .should().beFreeOfCycles()
            .check(classes);
  }

  @ArchTest
  void applicationFreeOfCycles(JavaClasses classes) {

    SlicesRuleDefinition.slices()
            .matching(basePackage + "feature.(*).application..")
            .should().beFreeOfCycles()
            .check(classes);
  }

  @ArchTest
  void aggregateDependencies(JavaClasses classes) {
    SlicesRuleDefinition.slices()
            .matching("tobyspring.splearn.domain.(*)..")
            .should(onlyCallReadMethodsOfOtherSlices())
            .check(classes);
  }

  private <SLICE extends Set<JavaClass>> ArchCondition<SLICE> onlyCallReadMethodsOfOtherSlices() {
    return new ArchCondition<>("다른 슬라이스의 getter 또는 레코드, Enum 메소드만 호출할 수 있다") {
      private final Set<JavaClass> classesInAnySlice = new HashSet<>();

      @Override
      public void init(Collection<SLICE> allSlice) {
        allSlice.forEach(classesInAnySlice::addAll);
      }

      @Override
      public void check(SLICE slice, ConditionEvents events) {
        for (JavaClass javaClass : slice) {
          for (JavaMethodCall call : javaClass.getMethodCallsFromSelf()) {
            JavaClass targetOwner = call.getTargetOwner();
            if (slice.contains(targetOwner)) continue;
            if (!classesInAnySlice.contains(targetOwner)) continue;
            if (targetOwner.isRecord()) continue;
            if (targetOwner.isEnum()) continue;

            String methodName = call.getTarget().getName();
            if (methodName.startsWith("get") || methodName.startsWith("is")
                    || methodName.startsWith("ensure")) continue;

            events.add(SimpleConditionEvent.violated(call, call.getDescription()));
          }
        }
      }
    };
  }

}
