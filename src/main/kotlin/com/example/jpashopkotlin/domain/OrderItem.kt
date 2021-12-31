package com.example.jpashopkotlin.domain

import javax.persistence.*

@Entity
class OrderItem(
    @Id @GeneratedValue @Column(name = "order_item_name")
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "item_id")
    var item: Item,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    var order: Order? = null,
    var orderPrice: Int?,
    var count: Int,
){
    // 생성메서드
    init{
        item.removeStock(count)
    }

    //== 비즈니스 로직 ==//
    fun cancel() {
        item.addStock(count)
    }

    //== 조회 로직 ==//
    fun getTotalPrice(): Int {
        orderPrice?.let { price ->
            return (price * count)
        }
        return 0
    }
}
