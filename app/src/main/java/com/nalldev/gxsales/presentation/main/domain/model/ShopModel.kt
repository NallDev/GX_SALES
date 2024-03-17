package com.nalldev.gxsales.presentation.main.domain.model

import androidx.annotation.DrawableRes

data class ShopModel(
    val title : String,
    val price : String,
    val stock : String,
    val type : String,
    val tax : String,
    @DrawableRes
    val imageId : Int
)