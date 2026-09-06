package clean.spring.study.learningtest.instancio;


import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

public class InstancioRunningTest {
    @Test
    void user() {
        TestUser user = Instancio.of(TestUser.class)
                .ignore(field(TestUser::getId))
                .generate(field(TestUser::getEmail), gen -> gen.net().email())
                .set(field(TestUser::getStatus), TestUserStatus.PENDING)
                .create();

        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isNotEmpty();
        assertThat(user.getName()).isNotEmpty();
        assertThat(user.getStatus()).isEqualTo(TestUserStatus.PENDING);

    }

    @Test
    void userModel() {

        Model<TestUser> model = Instancio.of(TestUser.class)
                .ignore(field(TestUser::getId))
                .generate(field(TestUser::getEmail), gen -> gen.net().email())
                .set(field(TestUser::getStatus), TestUserStatus.PENDING)
                .toModel();

        for (int i = 0; i < 100; i++) {

            TestUser user = Instancio.of(model).create();

            assertThat(user.getId()).isNull();
            assertThat(user.getEmail()).isNotEmpty();
            assertThat(user.getName()).isNotEmpty();
            assertThat(user.getStatus()).isEqualTo(TestUserStatus.PENDING);
        }
    }

    @Test
    void annotationValidation() {

        TestUserRegisterRequest request = Instancio.of(TestUserRegisterRequest.class)
                .create();

        assertThat(request.email()).isNotEmpty();
        assertThat(request.nickname()).isNotEmpty();
        assertThat(request.password()).hasSizeBetween(8, 100);

    }
}
