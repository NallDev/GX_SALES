package com.nalldev.gxsales.presentation.main.domain.model

import com.nalldev.gxsales.core.base.BaseItemModel

data class ProfileFormModel(
    val leadsDashboard : List<StatusesItem>,
    val profile : ProfileResponse,
    val branch : List<BaseItemModel>,
    val types : List<BaseItemModel>,
    val channels : List<BaseItemModel>,
    val medias : List<BaseItemModel>,
    val probabilities : List<BaseItemModel>,
    val statuses : List<BaseItemModel>,
    val sources : List<BaseItemModel>,
    val leads : List<LeadResponse>
)
