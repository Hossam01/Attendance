package com.john.attendance.repository.base

import com.weightwatchers.ww_exercise_01.api.ClientAPI
import com.weightwatchers.ww_exercise_01.api.base.BaseClientApi

abstract class ItemRepoBase(
    val client: BaseClientApi = ClientAPI()
) {

}