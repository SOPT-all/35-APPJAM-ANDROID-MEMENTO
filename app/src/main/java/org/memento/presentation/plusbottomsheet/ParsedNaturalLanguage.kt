package org.memento.presentation.plusbottomsheet

import java.time.LocalDateTime

data class ParsedDateResult(
    val title: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime? = null,
)

fun parseNaturalLanguage(
    input: String,
    isParseTime: Boolean = true // schedule 만 시간 파싱
): ParsedDateResult {
    val now = LocalDateTime.now()
    var text = input.trim()
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null

    // 1. 시간 범위 파싱
    if(isParseTime) {
        val timeRangeRegex = Regex("""(오전|오후)?\s?(\d{1,2})시(부터)?\s?(오전|오후)?\s?(\d{1,2})시까지""")
        val timeRangeMatch = timeRangeRegex.find(text)
        if (timeRangeMatch != null) {
            val (startMeridiem, startHourStr, _, endMeridiem, endHourStr) = timeRangeMatch.destructured
            val startHour = adjustHour(startHourStr.toInt(), startMeridiem)
            val endHour = adjustHour(endHourStr.toInt(), endMeridiem)

            startDate = now.withHour(startHour).withMinute(0).withSecond(0).withNano(0)
            endDate = now.withHour(endHour).withMinute(0).withSecond(0).withNano(0)

            text = text.replace(timeRangeRegex, "")
        }
    }

    // 2. 상대 날짜 처리
    val relativeMap = mapOf(
        "오늘" to 0,
        "내일" to 1,
        "모레" to 2
    )
    for ((keyword, daysToAdd) in relativeMap) {
        if (text.contains(keyword)) {
            startDate = now.plusDays(daysToAdd.toLong()).withHour(startDate?.hour ?: 0).withMinute(0)
            text = text.replace(keyword, "")
            break
        }
    }

    // 3. 구체적인 날짜 처리
    val specificDateRegex = Regex("""(?:(\d{4})년)?\s*(\d{1,2})월\s*(\d{1,2})일""")
    val dateMatch = specificDateRegex.find(text)
    if (dateMatch != null) {
        val (yearStr, monthStr, dayStr) = dateMatch.destructured
        val year = yearStr.ifBlank { now.year.toString() }.toInt()
        val month = monthStr.toInt()
        val day = dayStr.toInt()
        startDate = LocalDateTime.of(year, month, day, startDate?.hour ?: 0, 0)
        text = text.replace(specificDateRegex, "")
    }

    // 4. 요일 처리
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
    val weekdayRegex = Regex("""(이번주|다음주)?\s*(${daysOfWeek.joinToString("|")})요일""")
    val weekdayMatch = weekdayRegex.find(text)
    if (weekdayMatch != null) {
        val (weekContext, dayKor) = weekdayMatch.destructured
        val targetDay = daysOfWeek.indexOf(dayKor)
        val currentDay = now.dayOfWeek.value % 7

        val plusDays = when (weekContext) {
            "다음주" -> (7 - currentDay + targetDay + 7) % 7
            "이번주" -> (targetDay - currentDay + 7) % 7
            else -> if (targetDay <= currentDay) (targetDay - currentDay + 7) else (targetDay - currentDay)
        }
        startDate = now.plusDays(plusDays.toLong()).withHour(startDate?.hour ?: 0).withMinute(0)
        text = text.replace(weekdayRegex, "")
    }

    // 5. "다음달", "이번달", "내년", "작년" 처리
    when {
        text.contains("다음달") -> {
            val firstDay = now.plusMonths(1).withDayOfMonth(1)
            startDate = firstDay.withHour(startDate?.hour ?: 0).withMinute(0)
            text = text.replace("다음달", "")
        }
        text.contains("이번달") -> {
            val firstDay = now.withDayOfMonth(1)
            startDate = firstDay.withHour(startDate?.hour ?: 0).withMinute(0)
            text = text.replace("이번달", "")
        }
        text.contains("내년") -> {
            startDate = now.plusYears(1).withHour(startDate?.hour ?: 0).withMinute(0)
            text = text.replace("내년", "")
        }
        text.contains("작년") -> {
            startDate = now.minusYears(1).withHour(startDate?.hour ?: 0).withMinute(0)
            text = text.replace("작년", "")
        }
    }

    // 6. 단일 시간만 있을 경우
    if(isParseTime) {
        if (startDate == null) {
            val singleTimeRegex = Regex("""(오전|오후)?\s?(\d{1,2})시""")
            val singleMatch = singleTimeRegex.find(text)
            if (singleMatch != null) {
                val (meridiem, hourStr) = singleMatch.destructured
                val hour = adjustHour(hourStr.toInt(), meridiem)
                startDate = now.withHour(hour).withMinute(0).withSecond(0).withNano(0)
                text = text.replace(singleTimeRegex, "")
            }
        }
    }

    // 7. 불필요 단어 및 숫자 단위 제거
    val cleanupRegex = Regex("""(부터|까지|오전|오후|\d+시|\d+월|\d+일|\s+)""")
    val cleanTitle = text.replace(cleanupRegex, "").trim()

    return ParsedDateResult(
        title = if (cleanTitle.isNotEmpty()) cleanTitle else input,
        startDate = startDate,
        endDate = endDate
    )
}

private fun adjustHour(hour: Int, meridiem: String?): Int {
    return when (meridiem) {
        "오전" -> if (hour == 12) 0 else hour
        "오후" -> if (hour < 12) hour + 12 else hour
        else -> hour
    }
}
