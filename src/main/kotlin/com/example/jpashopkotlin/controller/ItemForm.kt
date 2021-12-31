package com.example.jpashopkotlin.controller

import javax.validation.constraints.NotEmpty

class ItemForm(
    @field:NotEmpty
    var name: String? = null,
    var price: Int? = null,
    var stockQuantity: Int? = null,
)
