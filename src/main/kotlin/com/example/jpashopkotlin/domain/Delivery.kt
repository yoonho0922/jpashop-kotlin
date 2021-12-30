package com.example.jpashopkotlin.domain

import javax.persistence.*

@Entity
class Delivery(
    @Id @GeneratedValue @Column(name = "delivery_id")
    var id: Long? = null,

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    var order: Order? = null,

    @Embedded
    var address: Address? = null,

    @Enumerated(EnumType.ORDINAL)
    var status: DeliveryStatus = DeliveryStatus.READY,
)
