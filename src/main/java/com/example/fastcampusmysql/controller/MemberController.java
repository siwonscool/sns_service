package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.MemberRegisterCommand;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    final private MemberWriteService memberWriteService;
    final private MemberReadService memberReadService;

    @PostMapping
    public MemberDto register(@RequestBody MemberRegisterCommand command){
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id){
        return memberReadService.getMember(id);
    }

    @PatchMapping("/{id}")
    public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname){
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }

    @GetMapping("/{memberId}/nickname-history")
    public List<MemberNicknameHistoryDto> getNicknameHistory(@PathVariable Long memberId){
        return memberReadService.getNicknameHistory(memberId);
    }
}
