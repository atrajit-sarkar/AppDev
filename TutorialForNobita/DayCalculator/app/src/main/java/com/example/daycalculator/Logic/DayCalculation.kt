package com.example.daycalculator.Logic

fun main() {
    val week = dayCalculation("13/08/2005")
    println(week)

}

fun dayCalculation(inputDOB: String): String {
    val parts = inputDOB.split("/")
    val dd = parts[0].toInt()
    var mm = parts[1].toInt()
    var yyyy = parts[2].toInt()

    if (mm > 2) {
        mm -= 2
    } else {
        yyyy -= 1
        if (mm == 1) {
            mm = 11
        } else if (mm == 2) {
            mm = 12
        }

    }

    val c = (yyyy / 100)
    val y = (yyyy % 100)

    var w = (dd + ((13 * mm - 1) / 5) - 2 * c + y + (c / 4) + (y / 4)) % 7

    if (w < 0) {
        w += 7
    }


    if (w == 0) {
        return "Sunday"
    } else if (w == 1) {
        return "Monday"

    } else if (w == 2) {
        return "Tuesday"
    } else if (w == 3) {
        return "Wednesday"
    } else if (w == 4) {
        return "Thursday"
    } else if (w == 5) {
        return "Friday"
    } else {
        return "Saturday"
    }

}