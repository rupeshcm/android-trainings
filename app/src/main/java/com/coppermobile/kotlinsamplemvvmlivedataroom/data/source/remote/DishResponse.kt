package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DishResponse : Serializable {

    @SerializedName("thumbnail")
    var thumbnail: String? = null
        private set

    @SerializedName("price")
    var price: Int? = -1
        private set

    @SerializedName("name")
    var name: String? = null
        private set

    @SerializedName("description")
    var description: String? = null
        private set

    @SerializedName("id")
    var id: Int? = -1
        private set
}