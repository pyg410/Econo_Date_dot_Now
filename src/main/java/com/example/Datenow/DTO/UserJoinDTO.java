package com.example.Datenow.DTO;

import com.example.Datenow.domain.User.Gender;
import com.example.Datenow.domain.User.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Builder
public class UserJoinDTO {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email; // 이메일
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password; // 패스워드
    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 입력해주세요.")
    private String name; // 이름
    @NotBlank(message = "생일을 입력해주세요.")
    private LocalDate birth; // 생일
    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private Long phoneNum; // 휴대폰 번호

    private String profileImg; // 프로필 사진

    @NotNull(message = "성별을 입력해주세요.") // 성별이 null값이면 안되지 않을까?
    private Gender gender; // 성별

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .birth(birth)
                .profileImg(profileImg)
                .gender(gender)
                .build();
    }
}
