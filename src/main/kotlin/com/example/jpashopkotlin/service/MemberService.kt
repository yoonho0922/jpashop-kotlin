package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.repository.MemberRepository
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun join(member: Member): Long? {
        validateDuplicateMember(member)
        memberRepository.save(member)
        return member.id
    }

    private fun validateDuplicateMember(member: Member) {
        member.name?.let { name ->
            val findMembers = memberRepository.findByName(name)
            if (findMembers.isNotEmpty()) {
                throw IllegalStateException("이미 존재하는 회원입니다.")
            }
        }
    }

    fun findMember(): List<Member> {
        return memberRepository.findAll()
    }

    fun findOne(memberId: Long): Member {
        return memberRepository.findOne(memberId)
    }

    fun update(id: Long, name: String) {
        var member = memberRepository.findOne(id)
        member.name = name
    }
}