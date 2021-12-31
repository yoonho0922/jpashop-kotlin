package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Address
import com.example.jpashopkotlin.domain.Item
import com.example.jpashopkotlin.domain.Member
import com.example.jpashopkotlin.domain.OrderStatus
import com.example.jpashopkotlin.exception.NotEnoughStockException
import com.example.jpashopkotlin.repository.OrderRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class OrderServiceTest{
    @Autowired lateinit var em: EntityManager
    @Autowired lateinit var orderService: OrderService
    @Autowired lateinit var orderRepository: OrderRepository

    @Test
    fun test(){
        // given
        val member = createMember()
        val book = createItem("코틀린 인액션", 31000, 90)
        val orderCount = 2

        // when
        val orderId = orderService.order(member.id, book.id, orderCount)

        // then
        orderId?.let { id ->
            val findOrder = orderRepository.findOne(id)

            findOrder?.let {
                assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, findOrder.orderStatus)
                assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, findOrder.orderItems.size)
                assertEquals("주문한 상품 수가 정확해야 한다.", orderCount, findOrder.orderItems[0].count)
                assertEquals("주문한 상품 가격이 정확해야 한다.", 31000, findOrder.orderItems[0].orderPrice)
                assertEquals("주문 가격은 가격 * 수량이다.", 31000 * orderCount, findOrder.getTotalPrice())
                assertEquals("주문 수량 만큼 재고가 줄어야 한다.", 90 - orderCount, book.stockQuantity)
            }
        }
    }

    @Test(expected = NotEnoughStockException::class)
    fun `상품주문 재고수량초과`(){
        // given
        val member = createMember()
        val book = createItem("코틀린 인액션", 31000, 9)
        val orderCount = 11

        // when
        orderService.order(member.id, book.id, orderCount)
        // then
        fail("재고 수량 부족 예외가 발생해야한다.")
    }

    @Test
    fun 주문취소(){
        // given
        val member = createMember()
        val book = createItem("코틀린 인액션", 31000, 9)
        val orderCount = 2

        val orderId = orderService.order(member.id, book.id, orderCount)

        // when
        orderId?.let {orderId ->
            orderService.cancelOrder(orderId)

            // then
            val findOrder = orderRepository.findOne(orderId)
            assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, findOrder!!.orderStatus)
            assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 9, book.stockQuantity)
        }

    }

    private fun createItem(name: String, price: Int, stockQuantity: Int): Item {
        val book = Item(
            name = name,
            price = price,
            stockQuantity = stockQuantity,
        )
        em.persist(book)
        return book
    }

    private fun createMember(): Member {
        val member = Member(name = "회원01")
        member.address = Address(
            city = "서울",
            street = "강남대로",
            zipcode = "123-333"
        )
        em.persist(member)
        return member
    }
}
