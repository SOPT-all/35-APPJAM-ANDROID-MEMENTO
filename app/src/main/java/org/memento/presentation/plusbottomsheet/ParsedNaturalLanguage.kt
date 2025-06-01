package org.memento.presentation.plusbottomsheet

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

// 재사용할 정규표현식 선언
private val TIME_RANGE_REGEX = Regex("""(오전|오후)?\s?(\d{1,2})시부터\s?(오전|오후)?\s?(\d{1,2})시까지""")
private val DATE_RANGE_REGEX = Regex("""(.+?)부터\s*(.+?)까지""")
private val TIL_REGEX = Regex("""(.+?)까지""")
private val KOR_WEEKDAY_REGEX = Regex("""(?:(이번주|다음주)?\s*(일|월|화|수|목|금|토)요일)""")
private val KOR_DATE_REGEX = Regex("""(어제|그제|오늘|내일|모레|\d+일\s*(?:전|후)|\d{1,2}월\s*\d{1,2}일)""")
private val ENG_DATE_REGEX = Regex(
    """(?i)(the day before yesterday|yesterday|today|tomorrow|day after tomorrow|\d+\s+days?\s+(?:after|before)|\d{1,2}[/-]\d{1,2}|[A-Za-z]+\s+\d{1,2}(?:st|nd|rd|th)?)"""
)

private val RELATIVE_KOR_REGEX = Regex("""(\d{1,2})일\s*(전|후)""")
private val RELATIVE_ENG_REGEX  = Regex("""(\d{1,2})\s+days?\s+(after|before)""")
private val MD_KOR_REGEX = Regex("""(\d{1,2})월\s*(\d{1,2})일""")
private val MD_SLASH_REGEX = Regex("""(\d{1,2})[/-](\d{1,2})(?:[/-](\d{4}))?""")
private val ENG_MONTH_DAY_REGEX = Regex("""([A-Za-z]+)\s+(\d{1,2})(?:st|nd|rd|th)?(?:,\s*(\d{4}))?""")
private val ENG_WEEKDAY_REGEX = Regex("""(?i)(this week|next week)?\s*(Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday)""")

fun parseNaturalLanguage(
    input: String,
    isParseTime: Boolean = true,
): ParsedDateResult {
    val now = LocalDateTime.now()
    var text = input.trim()
    var startDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null

    // 1) 시간 범위 파싱 (isParseTime == true 일 때만)
    if (isParseTime) {
        TIME_RANGE_REGEX.find(text)?.let { m ->
            val (sm, sh, em, eh) = m.destructured
            val startH = adjustHour(sh.toInt(), sm)
            val endH = adjustHour(eh.toInt(), em)
            startDate = now.withHour(startH).truncatedToDays()
            endDate = now.withHour(endH).truncatedToDays()
            text = text.replace(m.value, "")
        }
    }

    // 2-1) “~까지” 처리: start = 오늘, end = parseDateOnly(그 앞 표현)
    if (startDate == null) {
        DATE_RANGE_REGEX.find(text)?.let { m ->
            val (fromExpr, toExpr) = m.destructured
            // 그룹1: "X", 그룹2: "Y"를 각각 파싱
            startDate = parseDateOnly(fromExpr.trim(), now)
            endDate   = parseDateOnly(toExpr.trim(), now)
            text = text.replace(m.value, "")
        }
    }

    // 2-2) “~까지” 처리: start = 오늘, end = parseDateOnly(그 앞 표현)
    if (startDate == null) {
        TIL_REGEX.find(text)?.let { m ->
            startDate = now.truncatedToDays()
            endDate = parseDateOnly(m.groupValues[1].trim(), now)
            text = text.replace(m.value, "")
        }
    }

    // 3-1) 한국어 요일: “(이번주|다음주)? 수요일”
    if (startDate == null) {
        KOR_WEEKDAY_REGEX.find(text)?.let { m ->
            val expr = m.value.trim()
            startDate = parseDateOnly(expr, now)
            endDate = startDate
            text = text.replace(m.value, "")
        }
    }

    // 3-2) 한국어 상대/절대 날짜: “오늘”, “3일 후”, “5월 1일” 등
    if (startDate == null) {
        KOR_DATE_REGEX.find(text)?.let { m ->
            val expr = m.value.trim()
            startDate = parseDateOnly(expr, now)
            endDate = startDate
            text = text.replace(m.value, "")
        }
    }

    // 3-3) 영어 상대/절대 날짜: “tomorrow”, “2 days after”, “May 1st” 등
    if (startDate == null) {
        ENG_DATE_REGEX.find(text)?.let { m ->
            val expr = m.value.trim()
            startDate = parseDateOnly(expr, now)
            endDate = startDate
            text = text.replace(m.value, "")
        }
    }

    // 4) 나머지 불필요 키워드 제거 후 title 생성
    val cleanTitle =
        text
            .replace(Regex("""(부터|까지|\s+)"""), "")
            .trim()
            .ifEmpty { input.trim() }

    return ParsedDateResult(
        title = cleanTitle,
        startDate = startDate,
        endDate = endDate,
    )
}

/**
 * 다양한 표현들을 (한국어/영어, 상대/절대, 요일 등)을
 * 모두 LocalDateTime(0시)으로 변환해 주는 유틸 함수
 */
private fun parseDateOnly(
    expr: String,
    now: LocalDateTime,
): LocalDateTime {
    val e = expr.trim().toLowerCase()

    // Korean week
    if (e == "다음주") return now.plusWeeks(1).truncatedToDays()
    if (e == "이번주") return now.truncatedToDays()

    // English week
    if (e.equals("next week", true)) return now.plusWeeks(1).truncatedToDays()
    if (e.equals("this week", true)) return now.truncatedToDays()

    // Korean relative keywords
    mapOf("그제" to -2, "어제" to -1, "오늘" to 0, "내일" to 1, "모레" to 2)
        .takeIf { it.containsKey(expr) }
        ?.let { return now.plusDays(it[expr]!!.toLong()).truncatedToDays() }

    // English relative keywords
    when (e) {
        "the day before yesterday" -> return now.minusDays(2).truncatedToDays()
        "yesterday" -> return now.minusDays(1).truncatedToDays()
        "today" -> return now.truncatedToDays()
        "tomorrow" -> return now.plusDays(1).truncatedToDays()
        "day after tomorrow" -> return now.plusDays(2).truncatedToDays()
    }

    // “n일 전/후”
    RELATIVE_KOR_REGEX.find(expr)?.let {
        val (n, dir) = it.destructured
        val d = n.toLong() * if (dir == "후") 1 else -1
        return now.plusDays(d).truncatedToDays()
    }
    // “n days after/before”
    RELATIVE_ENG_REGEX.find(e)?.let {
        val (n, dir) = it.destructured
        val d = n.toLong() * if (dir == "after") 1 else -1
        return now.plusDays(d).truncatedToDays()
    }

    // “M월 D일”
    MD_KOR_REGEX.find(expr)?.let {
        val (m, d) = it.destructured
        return now.withMonth(m.toInt()).withDayOfMonth(d.toInt()).truncatedToDays()
    }
    // “MM/DD” or “M/D” (opt. YYYY)
    MD_SLASH_REGEX.find(e)?.let {
        val (m, d, y) = it.destructured
        val year = if (y.isBlank()) now.year else y.toInt()
        return LocalDateTime.of(year, m.toInt(), d.toInt(), 0, 0)
    }
    // “May 1st”, “Jan 2” 등
    val months = mapOf(
        "january" to 1, "february" to 2, "march" to 3, "april" to 4,
        "may" to 5, "june" to 6, "july" to 7, "august" to 8,
        "september" to 9, "october" to 10, "november" to 11, "december" to 12,
    )
    ENG_MONTH_DAY_REGEX.find(expr)?.let {
        val (mon, day, yearStr) = it.destructured
        val m = months[mon.toLowerCase()] ?: now.monthValue
        val y = if (yearStr.isBlank()) now.year else yearStr.toInt()
        return LocalDateTime.of(y, m, day.toInt(), 0, 0)
    }

    // 한국어 요일: “(이번주|다음주)? 수요일”
    KOR_WEEKDAY_REGEX.find(expr)?.let {
        val (ctx, dayKor) = it.destructured
        val idx = listOf("일", "월", "화", "수", "목", "금", "토").indexOf(dayKor)
        val weeks = if (ctx == "다음주") 1 else 0
        val todayIdx = now.dayOfWeek.value % 7
        val diff = if (idx >= todayIdx) {
            idx - todayIdx + weeks * 7
        } else {
            7 - (todayIdx - idx) + weeks * 7
        }
        return now.plusDays(diff.toLong()).truncatedToDays()
    }

    // 영어 요일: “this week Wednesday” / “next week Friday”
    ENG_WEEKDAY_REGEX.find(expr)?.let {
        val (ctx, dayEn) = it.destructured
        val dow  = DayOfWeek.valueOf(dayEn.toUpperCase())
        val base = if (ctx.equals("next week", true)) now.plusWeeks(1) else now
        return base.`with`(TemporalAdjusters.nextOrSame(dow)).truncatedToDays()
    }

    // 기본: 오늘
    return now.truncatedToDays()
}

// todo 의 경우는 자정으로 초기화
private fun LocalDateTime.truncatedToDays() =
    this.withHour(0).withMinute(0).withSecond(0).withNano(0)

// 오전/오후 파싱
private fun adjustHour(
    hour: Int,
    meridiem: String?,
): Int =
    when (meridiem) {
        "오전" -> if (hour == 12) 0 else hour
        "오후" -> if (hour < 12) hour + 12 else hour
        else   -> hour
    }
