package com.nalldev.gxsales.presentation.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.R
import com.nalldev.gxsales.presentation.main.domain.model.ShopModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ShopViewModel : ViewModel() {
    private val _listItemShop: MutableStateFlow<List<ShopModel>> = MutableStateFlow(emptyList())
    private val _title = MutableStateFlow("")

    val listItemShop = combine(
        _listItemShop,
        _title
    ) { listItem, title ->
        listItem.filter {
            it.title.contains(title, true)
        }
    }

    init {
        initData()
    }

    private fun initData() {
        _listItemShop.value = listOf(
            ShopModel(
                "A810R AC1200 Router",
                "Rp 56.000.000.000 / pcs",
                "3 Pcs",
                "Onu",
                "Rp 56.600 (11%)",
                R.drawable.image_shop1
            ),
            ShopModel(
                "A810R AC1200 Router",
                "Rp 56.000.000.000 / pcs",
                "3 Pcs",
                "Onu",
                "Rp 56.600 (11%)",
                R.drawable.image_shop2
            ),
            ShopModel(
                "A810R AC1200 Router",
                "Rp 56.000.000.000 / pcs",
                "3 Pcs",
                "Onu",
                "Rp 56.600 (11%)",
                R.drawable.image_shop3
            ),
            ShopModel(
                "A810R AC1200 Router",
                "Rp 56.000.000.000 / pcs",
                "3 Pcs",
                "Onu",
                "Rp 56.600 (11%)",
                R.drawable.image_shop4
            ),
        )
    }

    fun setTitle(title: String) = viewModelScope.launch(Dispatchers.IO) {
        _title.value = title
    }
}