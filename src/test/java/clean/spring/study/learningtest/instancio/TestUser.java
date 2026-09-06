package clean.spring.study.learningtest.instancio;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestUser {
    private Long id;
    private String name;
    private String email;
    private TestUserStatus status;
}

enum TestUserStatus {PENDING, ACTIVE, DEACTIVATED}
