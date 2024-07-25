package beyondProjectForOrdersystem.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberListResDto {
    private Long id;
    private String name;
    private String email;
    private String city;
    private Role role;
    private LocalDateTime createdTime;

}
