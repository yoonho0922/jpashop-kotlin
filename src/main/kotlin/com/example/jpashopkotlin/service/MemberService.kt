package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.repository.MemberRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import javax.transaction.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun join(member: Member): Long? {
        validateDuplicateMember(member) // name으로 중복 회원 검사
        memberRepository.save(member)
        return member.id
    }

    @Transactional
    fun update(id: Long, name: String) {
        val member = memberRepository.findOne(id)
        member.name = name
    }

    fun findMember(): List<Member> {
        return memberRepository.findAll()
    }

    fun findOne(memberId: Long): Member {
        return memberRepository.findOne(memberId)
    }

    private fun validateDuplicateMember(member: Member) {
        val findMembers = memberRepository.findByName(member.name)
        if (findMembers.isNotEmpty()) {
            throw IllegalStateException("이미 존재하는 회원입니다 : " + findMembers[0].name)
        }
    }
}
