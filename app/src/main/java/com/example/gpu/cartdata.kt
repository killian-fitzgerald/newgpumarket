package com.example.gpu

class cartdata {
    var imagecart: String? = null
    var namecart: String? = null
    var quantitycart: String? = null
    var pricecart: String? = null
    var totalpricecart: String? = null
    var description: String? = null
    var key: String? = null
    var datetime: String? = null

    constructor() {}
    constructor(
        imagecart: String?,
        namecart: String?,
        quantitycart: String?,
        pricecart: String?,
        totalpricecart: String?,
        description: String?,
        datetime: String?
    ) {
        this.imagecart = imagecart
        this.namecart = namecart
        this.quantitycart = quantitycart
        this.pricecart = pricecart
        this.totalpricecart = totalpricecart
        this.description = description
        this.datetime = datetime
    }

    @JvmName("getDatetime1")
    fun getDatetime(): String? {
        return datetime
    }

    @JvmName("getKey1")
    fun getKey(): String? {
        return key
    }

    @JvmName("setKey1")
    fun setKey(key: String?) {
        this.key = key
    }

    @JvmName("getImagecart1")
    fun getImagecart(): String? {
        return imagecart
    }

    @JvmName("setImagecart1")
    fun setImagecart(imagecart: String?) {
        this.imagecart = imagecart
    }

    @JvmName("getNamecart1")
    fun getNamecart(): String? {
        return namecart
    }

    @JvmName("setNamecart1")
    fun setNamecart(namecart: String?) {
        this.namecart = namecart
    }

    @JvmName("getQuantitycart1")
    fun getQuantitycart(): String? {
        return quantitycart
    }

    @JvmName("setQuantitycart1")
    fun setQuantitycart(quantitycart: String?) {
        this.quantitycart = quantitycart
    }

    @JvmName("getPricecart1")
    fun getPricecart(): String? {
        return pricecart
    }

    @JvmName("getDescription1")
    fun getDescription(): String? {
        return description
    }

    @JvmName("setDescription1")
    fun setDescription(description: String?) {
        this.description = description
    }

    @JvmName("setPricecart1")
    fun setPricecart(pricecart: String?) {
        this.pricecart = pricecart
    }

    @JvmName("getTotalpricecart1")
    fun getTotalpricecart(): String? {
        return totalpricecart
    }

    @JvmName("setTotalpricecart1")
    fun setTotalpricecart(totalpricecart: String?) {
        this.totalpricecart = totalpricecart
    }
}