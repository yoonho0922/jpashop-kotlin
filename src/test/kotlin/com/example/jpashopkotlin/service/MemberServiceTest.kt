package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Address
import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.repository.MemberRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
internal class MemberServiceTest{

    @Autowired
    lateinit var memberService: MemberService
    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun 회원가입() {
        // given
        val name = "yoonho"
        val member = Member(name = name)

        // when
        val savedId = memberService.join(member)

        // then
        assertEquals(member, memberRepository.findOne(savedId!!))
    }

    @Test(expected = IllegalStateException::class)
    fun 중복회원검사(){
        // given
        val name1 = "짱구"
        val member1 = Member(name = name1)
        val member2 = Member(name = name1)
        memberService.join(member1)

        // when
        memberService.join(member2)
        // then
        fail("중복 회원 예외가 발생해야한다.")
    }
}
