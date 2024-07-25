package beyondProjectForOrdersystem.member.service;

import beyondProjectForOrdersystem.common.dto.CommonResDto;
import beyondProjectForOrdersystem.member.domain.Member;
import beyondProjectForOrdersystem.member.domain.MemberListResDto;
import beyondProjectForOrdersystem.member.domain.MemberSaveReqDto;
import beyondProjectForOrdersystem.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CommonResDto memberCreate(MemberSaveReqDto dto){
//        비번 암호화하기~
        if(memberRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }
        Member member = dto.toEntity();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is successfully create",
                memberRepository.save(member));
        return commonResDto;
    }

    public CommonResDto memberList(Pageable pageable){
        Page<Member> memberLists = memberRepository.findAll(pageable);
        Page<MemberListResDto> memberListResDtos = memberLists.map(a->a.listFromEntity());
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "member is list success return",memberListResDtos);
        return commonResDto;
    }

}
