package stackpot.stackpot.service.PotMemberService;

import stackpot.stackpot.web.dto.PotMemberRequestDto;
import stackpot.stackpot.web.dto.PotMemberAppealResponseDto;

import java.util.List;

public interface PotMemberService {
    List<PotMemberAppealResponseDto> getPotMembers(Long potId);
    List<PotMemberAppealResponseDto> addMembersToPot(Long potId, PotMemberRequestDto requestDto);
    void updateAppealContent(Long potId, Long memberId, String appealContent);
    void validateIsOwner(Long potId); // 팟 생성자 검증
}
