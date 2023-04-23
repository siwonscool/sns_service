package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberRegisterCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

    public Member register(MemberRegisterCommand memberRegisterCommand){
        /*
        * 목표 - 회원정보(이메일, 닉네임, 생년월일)를 등록한다.
        *     - 닉네임은 10자를 넘을 수 없다.
        * 파라미터 memberRegisterCommand
        * var member = Member.of(memberRegisterCommand)
        * memberRepository.save()
        * */
        var member = Member.builder()
                .nickname(memberRegisterCommand.nickname())
                .email(memberRegisterCommand.email())
                .birthday(memberRegisterCommand.birthday())
                .build();

        var savedMember = memberRepository.save(member);
        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long id, String nickname){
        var member = memberRepository.findById(id).orElseThrow();
        member.changeNickname(nickname);
        memberRepository.save(member);
        saveMemberNicknameHistory(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }

}
