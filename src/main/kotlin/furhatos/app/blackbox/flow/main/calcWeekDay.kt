package furhatos.app.blackbox.flow.main
import furhatos.app.blackbox.flow.intents.DayEntity
import furhatos.app.blackbox.flow.intents.MonthEntity
import java.time.LocalDate


fun getWeekDay(day: Int, month : MonthEntity?, year: Int):String{
    val monthString = month.toString()
    val monthNumb = mapOf(
        "Januar" to 1, "Februar" to 2, "März" to 3, "April" to 4, "Mai" to 5, "Juni" to 6, "Juli" to 7,
        "August" to 8, "September" to 9, "Oktober" to 10, "November" to 11, "Dezember" to 12
    )
    val monthNumber = monthNumb[monthString] ?: throw IllegalArgumentException("Ungültiger Monat: $month")
    val dateNorm = LocalDate.of(year,monthNumber,day)
    val weekDayEN = dateNorm.dayOfWeek.toString()
    return germanDay(weekDayEN)




}
fun germanDay(weekDayEnglish: String):String {
    return when (weekDayEnglish.uppercase()){
        "MONDAY" -> "Montag"
        "TUESDAY" -> "Dienstag"
        "WEDNESDAY" -> "Mittwoch"
        "THURSDAY" -> "Donnerstag"
        "FRIDAY" -> "Freitag"
        "SATURDAY" -> "Samstag"
        "SUNDAY" -> "Sonntag"
        else -> "Ungültiger Wochentag"

    }
}







