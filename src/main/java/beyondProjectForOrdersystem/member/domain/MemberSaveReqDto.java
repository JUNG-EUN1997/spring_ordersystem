package beyondProjectForOrdersystem.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberSaveReqDto {
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;
    private Role role;

    public Member toEntity(){
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .city(this.city)
                .street(this.street)
                .zipcode(this.zipcode)
                .role(this.role)
                .build();
    }
}
