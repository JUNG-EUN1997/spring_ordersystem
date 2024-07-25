package beyondProjectForOrdersystem.member.controller;

import beyondProjectForOrdersystem.common.dto.CommonResDto;
import beyondProjectForOrdersystem.member.domain.MemberListResDto;
import beyondProjectForOrdersystem.member.domain.MemberSaveReqDto;
import beyondProjectForOrdersystem.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/member/create")
    public CommonResDto memberCreate(@RequestBody MemberSaveReqDto dto){
        return memberService.memberCreate(dto);
    }

    @GetMapping("/member/list")
    public CommonResDto memberList(
            @PageableDefault(page = 0, size=10, sort = "createdTime", direction = Sort.Direction.DESC )
            Pageable pageable){
        return memberService.memberList(pageable);
    }
}
