package beyondProjectForOrdersystem.member.service;

import beyondProjectForOrdersystem.member.domain.Member;
import beyondProjectForOrdersystem.member.dto.MemberResDto;
import beyondProjectForOrdersystem.member.dto.MemberSaveReqDto;
import beyondProjectForOrdersystem.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member memberCreate(MemberSaveReqDto dto){
        if(memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }
        Member member = dto.toEntity();
        memberRepository.save(member);
        return member;
    }

    public Page<MemberResDto> memberList(Pageable pageable){
        Page<Member> memberLists = memberRepository.findAll(pageable);
        Page<MemberResDto> memberListResDtos = memberLists.map(a->a.fromEntity());
        return memberListResDtos;
    }

}
