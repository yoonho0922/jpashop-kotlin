package com.example.jpashopkotlin.domain

import java.lang.IllegalStateException
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue @Column(name = "order_id")
    var id: Long? = null,
    member: Member? = null,
    delivery: Delivery? = null,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var orderItems: MutableList<OrderItem> = mutableListOf(),
    var orderDate: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus,
){
    //== 연관관계 메서드 ==//
    @ManyToOne(targetEntity = Member::class, fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    var member: Member? = member
        set(value) {
            field = value
            member?.orders?.add(this)
        }

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var delivery: Delivery? = delivery
        set(value) {
            field = value
            delivery?.order = this
        }

    private fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    //== 생성 메서드 ==//
    companion object{
        fun createOrder(member: Member, delivery: Delivery, orderItem: OrderItem): Order {
            val order = Order(
                member = member,
                delivery = delivery,
                orderStatus = OrderStatus.ORDER,
                orderDate = LocalDateTime.now(),
            )
            order.addOrderItem(orderItem)
            return order
        }
    }

    //== 비즈니스 로직 ==//
    fun cancel(){
        if (delivery?.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }

        this.orderStatus = OrderStatus.CANCEL
        for (orderItem in orderItems) {
            orderItem.cancel()
        }
    }

    //== 조회 로직 ==//
    fun getTotalPrice(): Int {
        var totalPrice = 0
        for (orderItem in orderItems) {
            totalPrice += orderItem.getTotalPrice()
        }
        return totalPrice
    }
}
