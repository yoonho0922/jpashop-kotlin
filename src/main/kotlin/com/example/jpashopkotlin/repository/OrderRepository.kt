package com.example.jpashopkotlin.repository

import com.example.jpashopkotlin.domain.Order
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

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

    fun findAllByString(orderSearch: OrderSearch): MutableList<Order?>? {
        var jpql = "select o from Order o join o.member m"
        var isFirstCondition = true

        //주문 상태 검색
        if (orderSearch.orderStatus != null) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " o.status = :status"
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.memberName)) {
            if (isFirstCondition) {
                jpql += " where"
                isFirstCondition = false
            } else {
                jpql += " and"
            }
            jpql += " m.name like :name"
        }
        var query: TypedQuery<Order?> = em.createQuery(jpql, Order::class.java)
            .setMaxResults(1000)
        if (orderSearch.orderStatus != null) {
            query = query.setParameter("status", orderSearch.orderStatus)
        }
        if (StringUtils.hasText(orderSearch.memberName)) {
            query = query.setParameter("name", orderSearch.memberName)
        }
        return query.resultList
    }

}
