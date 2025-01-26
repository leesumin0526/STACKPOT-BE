package stackpot.stackpot.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import stackpot.stackpot.domain.enums.Role;

@Getter
@Setter
@Builder
public class PotRecruitmentResponseDto {
    private Long recruitmentId;
    private String recruitmentRole;
    private Integer recruitmentCount;

}
