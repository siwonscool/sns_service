package com.example.fastcampusmysql.domain.member;


import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.stream.LongStream;

public class MemberTest {
    @DisplayName("회원은 닉네임을 변경할 수 있다")
    @Test
    public void testChangeName(){
        //given
        var member = MemberFixtureFactory.create();
        var expected = "pnu";

        //when
        member.changeNickname(expected);

        //then
        Assertions.assertEquals(expected, member.getNickname());
    }

    @DisplayName("닉네임은 10자를 넘을 수 없다")
    @Test
    public void testNicknameMaxLength(){
        //given
        var member = MemberFixtureFactory.create();

        //when
        var overMaxLengthName = "pnupnupnupnu";

        //then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthName)
        );
    }

}
