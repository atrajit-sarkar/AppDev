package com.example.tipapp.utils

import android.health.connect.datatypes.units.Percentage

fun calculateTotalTip(totalBill: Double, percentage: Int): Double {
    return if (totalBill>1 && totalBill.toString().isNotEmpty()){
        (totalBill*percentage)/100
    }else{
        0.0
    }


}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy:Int,
    tipPercentage: Int
):Double{
    val bill= calculateTotalTip(totalBill=totalBill, percentage = tipPercentage)+totalBill

    return  (bill/splitBy)
}