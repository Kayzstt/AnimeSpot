package com.example.animespot.screens.utils

import android.os.Build
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun stripHtml(html: String): String {
    return Jsoup.parse(html).text()
}
@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(timestamp: Int): String {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss 'UTC'")
    return Instant.ofEpochSecond(timestamp.toLong())
        .atOffset(ZoneOffset.UTC)
        .format(formatter)
}