package ru.stroesku.kmm.presentation.ui.utils

import java.text.SimpleDateFormat
import java.util.*

private const val TIME_MINUTE_SECONDS_FORMAT = "mm:ss"
private const val TIME_MINUTE = "mm"
private const val TIME_HOUR_MINUTE_FORMAT = "HH:mm"
private const val TIME_HOUR_MINUTE_DAY_FORMAT = "HH:mm EEEE"
private const val DATE_TIME_FORMAT = "yyyy-M-dd HH:mm"
private const val DATE_FORMAT = "y-MM-dd"
private const val DATE_DMY_FORMAT = "dd.MM.yyyy"
private const val DATE_DMYHHMM_FORMAT = "dd.MM.yyyy HH:mm"
private const val DATE_MONTH_FORMAT = "dd.MM"
private const val DATE_E_FORMAT = "E"

const val SECOND = 1000

private var timeMinuteSecondsFormatter =
    SimpleDateFormat(TIME_MINUTE_SECONDS_FORMAT, Locale.getDefault())
private var timeMinuteFormatter = SimpleDateFormat(TIME_MINUTE, Locale.getDefault())
private var timeHourMinuteFormatter = SimpleDateFormat(TIME_HOUR_MINUTE_FORMAT, Locale.getDefault())
private var timeDateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
private var timeYMDDateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
private var timeYMDHHMMDateFormatter = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
private var timeMMSSFormatter = SimpleDateFormat(TIME_MINUTE_SECONDS_FORMAT, Locale.getDefault())
private var timeHHMMFormatter = SimpleDateFormat(TIME_HOUR_MINUTE_FORMAT, Locale.getDefault())
private var timeHHMMDayWeekFormatter = SimpleDateFormat(TIME_HOUR_MINUTE_DAY_FORMAT, Locale.getDefault())
private var dateDMYFormatter = SimpleDateFormat(DATE_DMY_FORMAT, Locale.getDefault())
private var dateDMYHHMMFormatter = SimpleDateFormat(DATE_DMYHHMM_FORMAT, Locale.getDefault())
private var dateDMFormatter = SimpleDateFormat(DATE_MONTH_FORMAT, Locale.getDefault())
private var dateEFormatter = SimpleDateFormat(DATE_E_FORMAT, Locale.getDefault())

fun Date.timeMinuteSecondsFormat(): String = timeMinuteSecondsFormatter.format(this)

fun Date.timeMinuteFormat(): String = timeMinuteFormatter.format(this)

fun String.timeMinuteParse(): Date? = timeMinuteFormatter.parse(this)

fun Date.timeHourMinuteFormat(): String = timeHourMinuteFormatter.format(this)

fun Date.dateYMDFormat(): String = timeYMDDateFormatter.format(this)

fun Date.dateYMDHHMMFormat(): String = timeYMDHHMMDateFormatter.format(this)

fun Date.dateDMYFormat(): String = dateDMYFormatter.format(this)

fun Date.dateDMYHHMMFormat(): String = dateDMYHHMMFormatter.format(this)

fun Date.dateDMFormat(): String = dateDMFormatter.format(this)

fun Date.timeMMSSFormat(): String = timeMMSSFormatter.format(this)

fun Date.timeHHMMFormat(): String = timeHHMMFormatter.format(this)

fun String.timeHHMMParse(): Date? = timeHHMMFormatter.parse(this)

fun Date.timeHHMMDayWeekFormat(): String = timeHHMMDayWeekFormatter.format(this)

fun String.timeHHMMDayWeekParse(): Date? = timeHHMMDayWeekFormatter.parse(this)

fun String.dateDMYParse(): Date? = dateDMYFormatter.parse(this)

fun String.dateDMYHHMMParse(): Date? = dateDMYHHMMFormatter.parse(this)

fun String.dateYMDHHMMParse(): Date? = timeYMDHHMMDateFormatter.parse(this)

fun String.dateYMDParse(): Date? = timeYMDHHMMDateFormatter.parse(this)

fun String.dateYMDFormatParse(): Date? = timeYMDDateFormatter.parse(this)

fun getDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)

    return calendar.time
}

fun getDaysCount(fromDate: Long?, toDate: Long?, isPartDay: Boolean = false): Int {
    return if (fromDate == null || toDate == null) 0 else {
        val days = (kotlin.math.abs(fromDate - toDate) / (24 * 60 * 60 * 1000)).toInt()
        return if (isPartDay) days + 1 else days
    }
}
