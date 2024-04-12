package com.example.moneymanagement

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.time.LocalDate

data class Item(
    var ID: Int = 0,
    var Date: String = "",
    var Title: String = "",
    var Price: Int = 0,
    var Priority: String = "",
    var Category: String = ""
) {
    fun getDate(): LocalDate {
        return LocalDate.parse(Date)
    }
}

data class PieChartData(
    var Title: String = "",
    var Prices: Float = 0f
)

data class User(
    var ID: Int,
    var Username: String = "",
    var Password: String = "",
    var NickName: String = "",
    var TargetID: Int = 0,
    var Icon: String? = null
) {
    fun getIcon(resource: Resources): Bitmap {
        val baos = ByteArrayOutputStream()
        val notImage = BitmapFactory.decodeResource(resource, R.drawable.noimage)
        notImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val array = if (Icon.isNullOrEmpty()) {
            baos.toByteArray()
        } else {
            Base64.decode(Icon, 0)
        }
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }
}

data class SpinnerData(
    var ID: Int = 0,
    var Name: String = ""
) {
    override fun toString(): String {
        return Name
    }
}