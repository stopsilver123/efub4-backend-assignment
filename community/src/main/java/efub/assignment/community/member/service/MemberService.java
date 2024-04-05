package efub.assignment.community.member.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.dto.MemberUpdateRequestDto;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
   private final MemberRepository memberRepository;

    public Long signUp(SignUpRequestDto requestDto) {
        if (existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 email입니다." + requestDto.getEmail());
        }
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getMemberId();
    }
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    @Transactional(readOnly = true)
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원을 찾을 수 없습니다,id="+id));
    }

    public Long update(Long memberId, MemberUpdateRequestDto requestDto) {
        Member member = findMemberById(memberId);
        member.updateMember(requestDto.getNickname());
        return member.getMemberId();
    }

    public void withdraw(long memberId) {
        Member member = findMemberById(memberId);
        member.withdrawMember();
    }

    public void delete(long memberId) {
        Member member = findMemberById(memberId);
        memberRepository.delete(member);
    }
}
