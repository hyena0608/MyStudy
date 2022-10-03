package hello.login.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
}
