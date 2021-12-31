package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Item
import com.example.jpashopkotlin.repository.ItemRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ItemService(
    val itemRepository: ItemRepository,
) {
    @Transactional
    fun saveItem(item: Item): Long? {
        itemRepository.save(item)
        return item.id
    }

    @Transactional
    fun updateItem(itemId: Long, name: String?, price: Int?, stockQuantity: Int?){
        val findItem = itemRepository.findOne(itemId)
        findItem.name = name ?: findItem.name
        findItem.price = price
        findItem.stockQuantity = stockQuantity ?: 0
    }

    fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long): Item {
        return itemRepository.findOne(itemId)
    }
}
