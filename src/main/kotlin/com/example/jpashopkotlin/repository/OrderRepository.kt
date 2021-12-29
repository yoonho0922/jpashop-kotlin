package com.example.jpashopkotlin.repository

import com.example.jpashopkotlin.domain.Order
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class OrderRepository(
    val em: EntityManager
) {
    fun save(order: Order) {
        em.persist(order)
    }

    fun findOne(id: Long): Order? {
        return em.find(Order::class.java, id)
    }

}
