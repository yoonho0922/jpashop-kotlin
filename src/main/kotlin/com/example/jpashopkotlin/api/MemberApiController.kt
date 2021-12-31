package com.example.jpashopkotlin.api

import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.service.MemberService
import org.springframework.web.bind.annotation.*
import java.util.stream.Collector
import javax.validation.Valid
import kotlin.streams.toList

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {
    @GetMapping("/api/v1/members") // recursion 에러. member[orders] -> hibernate.. -> order[member] -> member[orders] -> hibernate..
    fun membersV1(): List<Member> {
        return memberService.findMember()
    }

    @GetMapping("/api/v2/members")
    fun membersV2(): Result<List<MemberDto>> {
        val findMembers = memberService.findMember()
        val collect = findMembers.stream().map { m ->
            MemberDto(name = m.name)
        }.toList()

        return Result(collect.size, collect)
    }

    @PostMapping("/api/v1/members") // todo 엔티티를 파라미터로 바로 받지 말것 -> v2 에 반영
    fun saveMemberV1(@RequestBody @Valid member: Member): CreateMemberResponse {
        val id = memberService.join(member)
        return CreateMemberResponse(id)
    }

    @PostMapping("/api/v2/members")
    fun saveMemberV2(@RequestBody @Valid request: CreateMemberRequest): CreateMemberResponse {
        val member = Member(
            name = request.name,
        )
        val id = memberService.join(member)
        return CreateMemberResponse(id)
      }

    @PutMapping("/api/v2/members/{id}")
    fun updateMemberV2(@PathVariable id: Long, @RequestBody @Valid request: UpdateMemberRequest): UpdateMemberResponse {

        memberService.update(id, request.name)
        val findMember = memberService.findOne(id)
        return UpdateMemberResponse(
            id = findMember.id,
            name = findMember.name,
        )
    }

    data class Result<T>(
        val count: Int,
        val data: T,
    )

    data class MemberDto(
        val name: String,
    )

    data class UpdateMemberRequest(
        val name: String,
    )

    data class UpdateMemberResponse(
        val id: Long?,
        val name: String,
    )

    data class CreateMemberRequest (
        val name: String,
    )

    data class CreateMemberResponse(
        val id: Long?,
    )
}
