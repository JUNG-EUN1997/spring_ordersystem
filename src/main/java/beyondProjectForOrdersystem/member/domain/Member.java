package beyondProjectForOrdersystem.member.domain;

import beyondProjectForOrdersystem.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String city;
    private String street;
    private String zipcode;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public MemberListResDto listFromEntity(){
        return MemberListResDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .city(this.city)
                .role(this.role)
                .createdTime(this.getCreatedTime())
                .build();
    }

}
