package com.zjta.wyb.kotlin

import okhttp3.MediaType
import okhttp3.RequestBody


fun Map<String, String>.generateRequestBody(): Map<String, RequestBody> {
    return this.mapValues {
        RequestBody.create(MediaType.parse("multipart/form-data"), this[it.value].toString())
    }
}