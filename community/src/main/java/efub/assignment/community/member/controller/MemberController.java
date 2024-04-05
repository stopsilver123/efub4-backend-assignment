package efub.assignment.community.member.controller;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberResponseDto;
import efub.assignment.community.member.dto.MemberUpdateRequestDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
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
        public MemberResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto){
            Long id = memberService.signUp(requestDto);
            Member findMember = memberService.findMemberById(id);
            return MemberResponseDto.from(findMember);
        }

        //멤버 조회 기능(1명)
        @GetMapping("/{member_id}")
        @ResponseStatus(value = HttpStatus.OK)
        public MemberResponseDto getMember(@PathVariable Long member_id){
            Member findMember = memberService.findMemberById(member_id);
            return MemberResponseDto.from(findMember);
        }

        //멤버 프로필 수정 (닉네임 수정 가능)
        @PatchMapping("/profile/{member_id}")
        @ResponseStatus(value = HttpStatus.OK)
        public MemberResponseDto update(@PathVariable final Long member_id, @RequestBody @Valid final MemberUpdateRequestDto requestDto){
            Long id = memberService.update(member_id, requestDto);
            Member findMember = memberService.findMemberById(id);
            return MemberResponseDto.from(findMember);
        }

        //멤버 삭제(휴면 계정으로)
        @PatchMapping("/{member_id}")
        @ResponseStatus(value = HttpStatus.OK)
        public String withdraw(@PathVariable long member_id){
            memberService.withdraw(member_id);
            return "삭제가 완료되었습니다.";
        }

        //멤버 삭제(db에서도 삭제)
        @DeleteMapping("/{member_id}")
        @ResponseStatus(value = HttpStatus.OK)
        public String delete(@PathVariable long member_id){
            memberService.delete(member_id);
            return "성공적으로 탈퇴되었습니다.(DB 삭제)";
        }
}


