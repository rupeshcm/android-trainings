package com.coppermobile.kotlinsamplemvvmlivedataroom.utils

import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish

interface IClickListener {
    fun onItemPressed(dish: Dish)
}