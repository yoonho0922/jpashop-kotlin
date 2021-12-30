package com.example.jpashopkotlin.controller

import com.example.jpashopkotlin.domain.Address
import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class MemberController(
    private val memberService: MemberService,
) {
    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm())
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid form: MemberForm, result: BindingResult): String {
        if (result.hasErrors()) {
            return "members/createMemberForm"
        }

        val member = Member()
        member.name = form.name
        member.address = Address(city = form.city, street = form.street, zipcode = form.zipcode)

        memberService.join(member)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members = memberService.findMember()
        model.addAttribute("members", members)
        return "members/memberList"
    }
}
