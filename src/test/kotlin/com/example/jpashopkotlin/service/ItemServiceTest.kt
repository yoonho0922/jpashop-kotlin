package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Item
import com.example.jpashopkotlin.repository.ItemRepository
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    lateinit var itemRepository: ItemRepository
    @Autowired
    lateinit var itemService: ItemService
    @Autowired
    lateinit var em: EntityManager

    @Test
    fun 아이템_추가(){
        // given
        val item = Item(
            name = "아몬드",
        )

        // when
        val savedId = itemService.saveItem(item)!!

        // then
        assertEquals(item, itemRepository.findOne(savedId))
    }
}
