package com.example.jpashopkotlin

import com.example.jpashopkotlin.domain.*
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Component
class InitDb(
    private val initService: InitService
) {

    @PostConstruct
    fun init(){
        initService.dbInit1()
        initService.dbInit2()
    }

    @Component
    @Transactional
    class InitService(
        private val em: EntityManager,
    ){
        fun dbInit1(){
            val member = createMember("userA", "서울", "강남대로", "13232")
            em.persist(member)

            val item1 = Item(
                name = "부자 아빠",
                price = 10000,
                stockQuantity = 100,
            )
            em.persist(item1)

            val item2 = Item(
                name = "이기적 유전자",
                price = 13000,
                stockQuantity = 70,
            )
            em.persist(item2)

            val orderItem1 = OrderItem(
                item = item1,
                orderPrice = item1.price,
                count = 1,
            )
            val orderItem2 = OrderItem(
                item = item2,
                orderPrice = item2.price,
                count = 2,
            )

            val delivery = Delivery(
                address = member.address,
            )

            val order1 = Order.createOrder(
                member = member,
                delivery = delivery,
                orderItem = orderItem1
            )
            val order2 = Order.createOrder(
                member = member,
                delivery = delivery,
                orderItem = orderItem2
            )

            em.persist(order1)
            em.persist(order2)
        }

        fun dbInit2(){
            val member = createMember("userB", "부산", "광안로", "6472")
            em.persist(member)

            val item1 = Item(
                name = "선형대수학",
                price = 30000,
                stockQuantity = 300,
            )
            em.persist(item1)

            val item2 = Item(
                name = "미적분2",
                price = 26000,
                stockQuantity = 190,
            )
            em.persist(item2)

            val orderItem1 = OrderItem(
                item = item1,
                orderPrice = item1.price,
                count = 9,
            )
            val orderItem2 = OrderItem(
                item = item2,
                orderPrice = item2.price,
                count = 11,
            )

            val delivery = Delivery(
                address = member.address,
            )

            val order1 = Order.createOrder(
                member = member,
                delivery = delivery,
                orderItem = orderItem1
            )
            val order2 = Order.createOrder(
                member = member,
                delivery = delivery,
                orderItem = orderItem2
            )

            em.persist(order1)
            em.persist(order2)
        }

        private fun createMember(name: String, city: String, street: String, zipcode: String): Member {
            val member = Member(
                name = name,
            )
            member.address = Address(
                city = city,
                street = street,
                zipcode = zipcode,
            )
            return member
        }
    }
}
