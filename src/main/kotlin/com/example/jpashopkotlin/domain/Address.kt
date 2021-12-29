package com.example.jpashopkotlin.domain

import javax.persistence.Embeddable

@Embeddable
class Address(
    private var city: String?,
    private var street: String?,
    private var zipcode: String?,
)
// todo protected constructor()
