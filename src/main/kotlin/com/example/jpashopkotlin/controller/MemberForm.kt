package com.example.jpashopkotlin.controller

import javax.validation.constraints.NotEmpty



data class MemberForm(
    @field:NotEmpty
    var name: String? = null,
    var city: String? = null,
    var street: String? = null,
    var zipcode: String? = null,
)
