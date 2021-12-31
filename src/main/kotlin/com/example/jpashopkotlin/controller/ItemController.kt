package com.example.jpashopkotlin.controller

import com.example.jpashopkotlin.domain.Item
import com.example.jpashopkotlin.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class ItemController(
    private val itemService: ItemService
) {
    @GetMapping("/items/new")
    fun createForm(model: Model): String {
        model.addAttribute("form", ItemForm())
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(@Valid form: ItemForm): String {
        val book = Item(
            name = form.name!!, // valid가 not null 보장
            price = form.price,
            stockQuantity = form.stockQuantity ?: 0,
        )
        itemService.saveItem(book)
        return "redirect:/items"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemList"
    }

    @GetMapping("items/{itemId}/edit")
    fun updateItemForm(@PathVariable("itemId") itemId: Long, model: Model): String? {
        val item: Item = itemService.findOne(itemId)
        val form = ItemForm(
            name = item.name,
            price = item.price,
            stockQuantity = item.stockQuantity,
        )
        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("items/{itemId}/edit")
    fun updateItem(@PathVariable itemId: Long, @ModelAttribute("form") form: ItemForm): String {
        itemService.updateItem(
            itemId = itemId,
            name = form.name,
            price = form.price,
            stockQuantity = form.stockQuantity
        )
        return "redirect:/items"
    }
}
