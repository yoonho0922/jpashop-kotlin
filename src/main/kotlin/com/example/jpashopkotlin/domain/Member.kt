package com.example.jpashopkotlin.domain

import javax.persistence.*

@Entity
class Member(
    @Id @GeneratedValue @Column(name = "member_id")
    var id: Long? = null,
    var name: String, // name은 필수 입력
    @Embedded
    var address: Address? = null,
    @OneToMany(mappedBy = "member")
    var orders: MutableList<Order> = mutableListOf(),
)
