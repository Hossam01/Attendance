package com.john.attendance.repository

import com.weightwatchers.ww_exercise_01.api.ClientAPI
import com.weightwatchers.ww_exercise_01.api.base.BaseClientApi

import com.john.attendance.repository.base.ItemRepoBase
import okhttp3.internal.http2.ErrorCode
import okhttp3.internal.http2.StreamResetException

class ItemsRepo(clientAPI: BaseClientApi = ClientAPI()) : ItemRepoBase(client = clientAPI) {

}