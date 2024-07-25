package beyondProjectForOrdersystem.member.controller;

import beyondProjectForOrdersystem.common.dto.CommonResDto;
import beyondProjectForOrdersystem.member.domain.Member;
import beyondProjectForOrdersystem.member.dto.MemberResDto;
import beyondProjectForOrdersystem.member.dto.MemberSaveReqDto;
import beyondProjectForOrdersystem.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@Controller
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/member/create")
    public ResponseEntity<?> memberCreate(@Valid @RequestBody MemberSaveReqDto dto){ // dto단에 validation을 체크하려면
        Member member = memberService.memberCreate(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,
                "member is successfully create", member.getId());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("/member/list")
    public ResponseEntity<?> memberList(
            @PageableDefault(page = 0, size=10, sort = "createdTime", direction = Sort.Direction.DESC )
            Pageable pageable){

        Page<MemberResDto> memberListResDtos =  memberService.memberList(pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,
                "member are found", memberListResDtos);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }
}
