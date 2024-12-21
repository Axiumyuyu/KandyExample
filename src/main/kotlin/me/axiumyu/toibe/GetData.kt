package me.axiumyu.toibe

import org.jsoup.Jsoup
import java.time.LocalDate

class GetData {

    fun fetchTiobeData(): Map<String, Int> {
        val url = "https://www.tiobe.com/tiobe-index/"
        val doc = Jsoup.connect(url).get()
        val table =
            doc.select("table.table-top20").first() ?: throw IllegalArgumentException("Cannot find ranking table")

        return table.select("tr").drop(1).associate {
            val cols = it.select("td")
            val rank = cols[0].text().toInt()
            val language = cols[3].text()
            language to rank
        }
    }

    fun getHistoricalData(): Map<String, Map<LocalDate, Int>> {
        val languages = listOf("Java", "Kotlin", "JavaScript", "TypeScript")
        val years = 2019..2023
        val result = mutableMapOf<String, MutableMap<LocalDate, Int>>()

        for (lang in languages) result[lang] = mutableMapOf()

        for (year in years) {
            for (month in listOf(1, 7)) {
                try {
                    val data = fetchTiobeData()
                    val date = LocalDate.of(year, month, 1)
                    for (lang in languages) {
                        data[lang]?.let { rank -> result[lang]!![date] = rank }
                    }
                } catch (e: Exception) {
                    println("Error fetching data for $year-$month: ${e.message}")
                }
            }
        }

        return result
    }
}

data class Ranking(val language: String, val rank: Int, val date: LocalDate)