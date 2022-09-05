package com.example.gpu

class data {
    var image: String? = null
    var description: String? = null
    var price: String? = null
    var name: String? = null
    var discount: String? = null
    var key: String? = null

    internal constructor() {}
    constructor(
        image: String?,
        description: String?,
        price: String?,
        name: String?,
        discount: String?
    ) {
        this.image = image
        this.description = description
        this.price = price
        this.name = name
        this.discount = discount
    }

    @JvmName("getImage1")
    fun getImage(): String? {
        return image
    }

    @JvmName("getDescription1")
    fun getDescription(): String? {
        return description
    }

    @JvmName("getDiscount1")
    fun getDiscount(): String? {
        return discount
    }

    @JvmName("getPrice1")
    fun getPrice(): String? {
        return price
    }

    @JvmName("getName1")
    fun getName(): String? {
        return name
    }

    @JvmName("getKey1")
    fun getKey(): String? {
        return key
    }

    @JvmName("setKey1")
    fun setKey(key: String?) {
        this.key = key
    }
}