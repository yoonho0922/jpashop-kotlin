package com.example.jpashopkotlin.service

import com.example.jpashopkotlin.domain.Item
import com.example.jpashopkotlin.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    val itemRepository: ItemRepository
) {
    fun saveItem(item: Item): Long? {
        itemRepository.save(item)
        return item.id
    }

    fun updateItem(itemId: Long, name: String, price: Int, stockQuantity: Int){
        val findItem = itemRepository.findOne(itemId)
        findItem.name = name
        findItem.price = price
        findItem.stockQuantity = stockQuantity
    }

    fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long): Item {
        return itemRepository.findOne(itemId)
    }
}
