package com.example.moneymanagement

import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object GetObject {
    fun userCheck(username: String): Boolean {
        return Api.select<Boolean>("check/user?username=$username")
    }

    fun createUser(username: String, password: String, nickname: String): String {
        return Api.request("create/user?username=$username&password=$password&nickname=$nickname")
    }

    fun showPassword(username: String): String {
        return Api.select<String>("check/password?username=$username")
    }

    fun login(username: String, password: String): String {
        return Api.request("post/login?username=$username&password=$password")
    }

    fun getUser(id: Int): User {
        return Api.select<User>("get/user?id=$id")
    }

    fun editCheck(id: Int, username: String): Boolean {
        return Api.select<Boolean>("check/edit/user?username=$username&id=$id")
    }

    fun editUser(user: User): String {
        return Api.request("edit/user", Gson().toJson(user))
    }

    fun getTarget(): List<SpinnerData> {
        return Api.select<List<SpinnerData>>("get/target")
    }

    fun getItem(id: Int): List<Item> {
        return Api.select<List<Item>>("get/items?id=$id")
    }

    fun getCurrentItem(list: List<Item>): List<Item> {
        val format = DateTimeFormatter.ofPattern("yyyy-MM")
        return list.filter { it.getDate().format(format) == LocalDate.now().format(format) }
    }

    fun editTarget(userID: Int, targetID: Int): String {
        return Api.request("edit/target?userid=$userID&targetid=$targetID")
    }

    fun getPriority(): List<SpinnerData> {
        return Api.select("get/priority")
    }
    fun getCategory(): List<SpinnerData> {
        return Api.select("get/category")
    }
}