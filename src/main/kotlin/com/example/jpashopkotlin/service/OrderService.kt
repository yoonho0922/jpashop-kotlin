package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Delivery
import com.example.jpashopkotlin.domain.Order
import com.example.jpashopkotlin.domain.OrderItem
import com.example.jpashopkotlin.repository.ItemRepository
import com.example.jpashopkotlin.repository.MemberRepository
import com.example.jpashopkotlin.repository.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OrderService (
    val orderRepository: OrderRepository,
    val memberRepository: MemberRepository,
    val itemRepository: ItemRepository,
){
    @Transactional
    fun order(memberId: Long?, itemId: Long?, count: Int): Long? {
        // 엔티티 조회
        val member = memberId?.let { memberRepository.findOne(it) } ?: return null
        val item = itemId?.let { itemRepository.findOne(it) } ?: return null

        // 배송정보 생성
        val delivery = Delivery()
        delivery.address = member.address

        // 주문상품 생성
        val orderItem = OrderItem.createOrderItem(
            item = item,
            orderPrice = item.price,
            count = count
        )

        val order = Order.createOrder(
            member = member,
            delivery = delivery,
            orderItem = orderItem
        )
        // 주문 저장
        orderRepository.save(order)

        return order.id
    }

    fun cancelOrder(orderId: Long) {
        val order = orderRepository.findOne(orderId)
        order?.cancel()
    }
}
