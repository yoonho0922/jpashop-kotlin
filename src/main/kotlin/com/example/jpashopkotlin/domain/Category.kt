package com.example.jpashopkotlin.domain

import javax.persistence.*

@Entity
class Category(
    @Id @GeneratedValue @Column(name = "category_id")
    private var id: Long,

    private var name: String,

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    private var items: MutableList<Item>,

    // 카테고리 안에 카테고리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private var parent: Category,

    @OneToMany(mappedBy = "parent")
    private var child: MutableList<Category>
)
