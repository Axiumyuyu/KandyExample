package me.axiumyu.toibe

import org.jsoup.Jsoup
import java.util.regex.Pattern

fun main() {
    val headers = mapOf(
        "User-Agent" to "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1"
    )

    val url = "https://www.tiobe.com/tiobe-index/"
    val repRaw = Jsoup.connect(url).headers(headers).get()
    val rep = Jsoup.connect(url).headers(headers).get().body().text()
    val table = repRaw.allElements.find { it.id() == "tiobeindex" }?.select("table")?.first()

    // 正则匹配提取数据
    val dataPattern = Pattern.compile("\\{name : (.*?),data : (.*?)}")
    val matcher = dataPattern.matcher(rep)
    val programing = mutableListOf<String>()
    val dates = mutableListOf<String>()

    while (matcher.find()) {
        programing.add(matcher.group(1).removeSurrounding("\""))
        dates.add(matcher.group(2))
    }

    // 用集合存储提取的数据
    val dataCollection = mutableListOf<Map<String, String>>()

    for (x in dates.indices) {
        val name = programing[x]
        val datasPattern = Pattern.compile("\\[Date.UTC(.*?)]", Pattern.DOTALL)
        val datasMatcher = datasPattern.matcher(dates[x])

        while (datasMatcher.find()) {
            val dateNumbers = datasMatcher.group(1).split(",").map { it.trim().toInt() }
            val date2 = "${dateNumbers[0]}-${dateNumbers[1]}-${dateNumbers[2]}" // 拼接得到时间
            val dataPer = "${dateNumbers[dateNumbers.size - 2]}.${dateNumbers[dateNumbers.size - 1]}" // 得到热度数据

            val dataMap = mapOf(
                "Programing" to name,
                "Date" to date2,
                "Data_Per" to dataPer
            )
            dataCollection.add(dataMap)

            println("[Programing: $name, Date: $date2, Data_Per: $dataPer]")
        }
    }

    // 输出集合中的所有数据
    dataCollection.forEach { println(it) }
}
