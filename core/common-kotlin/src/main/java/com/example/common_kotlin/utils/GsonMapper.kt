package com.example.common_kotlin.utils

import com.example.common_kotlin.domain.model.ArticleModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun ArticleModel.toJson(): String =
    URLEncoder.encode(Gson().toJson(this), StandardCharsets.UTF_8.toString())

fun String.toArticleDataModel(): ArticleModel =
    Gson().fromJson(URLDecoder.decode(this, StandardCharsets.UTF_8.toString()), ArticleModel::class.java)