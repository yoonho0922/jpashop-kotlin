package com.example.jpashopkotlin.domain

import javax.persistence.*

@Entity
class Member(
    @Id @GeneratedValue @Column(name = "member_id")
    var id: Long? = null,
    var name: String? = null,
    @Embedded
    var address: Address? = null,
    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = mutableListOf(),
)
