package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.repository.MemberRepository
import junit.framework.Assert.assertEquals
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
        val member = Member()
        member.name = "yoonho"

        // when
        val savedId = memberService.join(member)

        // then
        assertEquals(member, memberRepository.findOne(savedId!!))
    }

}
