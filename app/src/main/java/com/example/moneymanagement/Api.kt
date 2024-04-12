package com.example.moneymanagement

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

object Api {
    fun request(arg: String, json: String = ""): String {
        val url = URL("http://mysv.meloves.net:8002/$arg")
        val con = url.openConnection() as HttpURLConnection
        con.apply {
            requestMethod = "POST"
            connectTimeout = 3000
            if (json.isNotEmpty()) {
                doOutput = true
                setRequestProperty("Content-Type", "application/json;charset=utf-8")
                OutputStreamWriter(outputStream, StandardCharsets.UTF_8).use { it.write(json) }
            }
        }
        return BufferedReader(
            InputStreamReader(
                con.inputStream,
                StandardCharsets.UTF_8
            )
        ).use { it.readText() }
    }

    val gson = Gson()
    inline fun <reified T> select(arg: String): T =
        gson.fromJson(request(arg), object : TypeToken<T>() {}.type)
}