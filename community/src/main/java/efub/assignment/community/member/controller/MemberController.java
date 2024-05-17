package efub.assignment.community.member.controller;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.MemberUpdateRequestDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.dto.SignUpResponseDto;
import efub.assignment.community.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
        //멤버 생성 기능
        @PostMapping
        @ResponseStatus(value = HttpStatus.CREATED)
        public SignUpResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto){
            Long id = memberService.signUp(requestDto);
            Member findMember = memberService.findMemberById(id);
            return SignUpResponseDto.builder()
                    .memberId(findMember.getMemberId())
                    .email(findMember.getEmail())
                    .nickname(findMember.getNickname())
                    .university(findMember.getUniversity())
                    .studentId(findMember.getStudentId())
                    .build();
        }

        //멤버 조회 기능(1명)
        @GetMapping("/{memberId}")
        @ResponseStatus(value = HttpStatus.OK)
        public MemberResponseDto getMember(@PathVariable Long memberId){
            Member findMember = memberService.findMemberById(memberId);
            return MemberResponseDto.builder()
                    .memberId(findMember.getMemberId())
                    .email(findMember.getEmail())
                    .nickname(findMember.getNickname())
                    .university(findMember.getUniversity())
                    .studentId(findMember.getStudentId())
                    .build();
        }

        //멤버 프로필 수정 (닉네임 수정 가능)
        @PatchMapping("/profile/{memberId}")
        @ResponseStatus(value = HttpStatus.OK)
        public MemberResponseDto update(@PathVariable final Long memberId, @RequestBody @Valid final MemberUpdateRequestDto requestDto){
            Long id = memberService.update(memberId, requestDto);
            Member findMember = memberService.findMemberById(id);
            return MemberResponseDto.builder()
                    .memberId(findMember.getMemberId())
                    .email(findMember.getEmail())
                    .nickname(findMember.getNickname())
                    .university(findMember.getUniversity())
                    .studentId(findMember.getStudentId())
                    .build();
        }

        //멤버 삭제(휴면 계정으로)
        @PatchMapping("/{memberId}")
        @ResponseStatus(value = HttpStatus.OK)
        public String withdraw(@PathVariable long memberId){
            memberService.withdraw(memberId);
            return "삭제가 완료되었습니다.";
        }

        //멤버 삭제(db에서도 삭제)
        @DeleteMapping("/{memberId}")
        @ResponseStatus(value = HttpStatus.OK)
        public String delete(@PathVariable long memberId){
            memberService.delete(memberId);
            return "성공적으로 탈퇴되었습니다.(DB 삭제)";
        }
}


