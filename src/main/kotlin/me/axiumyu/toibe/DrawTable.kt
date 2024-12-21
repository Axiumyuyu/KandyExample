package me.axiumyu.toibe

import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.letsplot.layers.line


fun main(args: Array<String>) {


    val years by columnOf("2018", "2019", "2020", "2021", "2022")
    val cost by columnOf(62.7, 64.7, 72.1, 73.7, 68.5)
    val df = dataFrameOf(years, cost)

    df.plot {
        line {
            x(years)
            y(cost)
        }
    }
}