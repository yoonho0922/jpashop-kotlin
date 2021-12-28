package com.example.jpashopkotlin.domain

import com.example.jpashopkotlin.domain.Category
import javax.persistence.*

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "dtype")
data class Item(
    @Id @GeneratedValue @Column(name = "item_id")
    var id: Long? = null,

    var name: String? = null,

    var price: Int? = null,

    var stockQuantity: Int? = 0,

    @ManyToMany(mappedBy = "items")
    var categories: MutableList<Category> = mutableListOf(),
)
//
//@Entity
//data class Book(
//    private var id: Long,
//
//    private var name: String,
//
//    private var price: Int,
//
//    private var stockQuantity: Int,
//
//    @ManyToMany(mappedBy = "items")
//    private var categories: MutableList<Category>,
//
//    private var author: String,
//    private var isbn: String,
//) : Item(id, name, price, stockQuantity, categories)
