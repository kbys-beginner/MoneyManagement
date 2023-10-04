package com.example.moneymanagement

import java.time.LocalDate

data class Data(
    var ID: Int = 0,
    var Year: String = "",
    var Month: String = "",
    var Day: String = "",
    var Title: String = "",
    var Price: Int = 0,
    var PriorityID: Int = 0,
    var Priority: String = "",
    var CategoryID: Int = 0,
    var Category: String = ""
)


data class SpinnerData(
    var ID: Int = 0,
    var Name: String = ""
) {
    override fun toString(): String {
        return Name
    }
}