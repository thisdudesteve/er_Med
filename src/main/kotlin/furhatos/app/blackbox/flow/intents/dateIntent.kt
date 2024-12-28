package furhatos.app.blackbox.flow.intents

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

// Define the DateIntent classs
class DateIntent(
    val day: DayEntity? = null,
    val month: MonthEntity? = null,
) : Intent() {
    override fun getConfidenceThreshold(): Double {
        return 0.7
    }


    // Only provide examples in German
    override fun getExamples(lang: Language): List<String> {
        return if (lang == Language.GERMAN) {
            listOf(
                "Heute ist der @day @month 2024.",
                "Es ist der @day @month 2024.",
                "Am @day @month 2024",
                "@day @month 2024",
                "Der @day @month",
                "Der erste erste 2024",
                "Es ist der erste zwölfte ",
                "Es ist der erste @month ",
                "@day @month",
                "vierter August",
                "erster Januar",
            )
        } else {
            emptyList() // Return an empty list for other languages (not needed here)
        }
    }
}

// Define the DayEntity class
class DayEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return if (lang == Language.GERMAN) {
            listOf(
                "1:erste,erster,Erste,Erster,ersta",
                "2:zweite,zweiter,Zweite,Zweiter,2,zweita",
                "3:dritte,dritter,Dritte,Dritter,3,dritta",
                "4:vierte,vierter,Vierte,Vierter,4,vierta",
                "5:fünfte,fünfter,Fünfte,Fünfter,5,fünfta",
                "6:sechste,sechster,Sechste,Sechster,6,sechsta",
                "7:siebte,siebter,Siebte,Siebter,7",
                "8:achte,achter,Achste,Achster,8",
                "9:neunte,neunter,Neunte,Neunter,9",
                "10:zehnte,zehnter,Zehnte,Zehnter,10",
                "11:elfte,elfter,Elfte,Elfter,11",
                "12:zwölfte,zwölfter,Zwölfte,Zwölfter,12",
                "13:dreizehnte,dreizehnter,Dreizehnte,Dreizehnter,13",
                "14:vierzehnte,vierzehnter,Vierzehnte,Vierzehnter,14",
                "15:fünfzehnte,fünfzehnter,Fünfzehnte,Fünfzehnter,15",
                "16:sechzehnte,sechzehnter,Sechzehnte,Sechzehnter,16",
                "17:siebzehnte,siebzehnter,Siebzehnte,Siebzehnter,17",
                "18:achtzehnte,achtzehnter,Achtzehnte,Achtzehnter,18",
                "19:neunzehnte,neunzehnter,Neunzehnte,Neunzehnter,19",
                "20:zwanzigste,zwanzigster,Zwanzigste,Zwanzigster,20",
                "21:einundzwanzigste,einundzwanzigster,Einundzwanzigste,Einundzwanzigster,21",
                "22:zweiundzwanzigste,zweiundzwanzigster,Zweiundzwanzigste,Zweiundzwanzigster,22",
                "23:dreiundzwanzigste,dreiundzwanzigster,Dreiundzwanzigste,Dreiundzwanzigster,23",
                "24:vierundzwanzigste,vierundzwanzigster,Vierundzwanzigste,Vierundzwanzigster,24",
                "25:fünfundzwanzigste,fünfundzwanzigster,Fünfundzwanzigste,Fünfundzwanzigster,25",
                "26:sechsundzwanzigste,sechsundzwanzigster,Sechsundzwanzigste,Sechsundzwanzigster,26",
                "27:siebenundzwanzigste,siebenundzwanzigster,Siebenundzwanzigste,Siebenundzwanzigster,27",
                "28:achtundzwanzigste,achtundzwanzigster,Achtundzwanzigste,Achtundzwanzigster,28",
                "29:neunundzwanzigste,neunundzwanzigster,Neunundzwanzigste,Neunundzwanzigster,29",
                "30:dreißigste,dreißigster,Dreißigste,Dreißigster,30",
                "31:einunddreißigste,einunddreißigster,Einunddreißigste,Einunddreißigster,31"




            )
        } else {
            emptyList() // Return an empty list for non-German languages
        }
    }
}

// Define the MonthEntity class
class MonthEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return if (lang == Language.GERMAN) {
            listOf(
                "1:Januar",
                "2:Februar",
                "3:März",
                "4:April",
                "5:Mai",
                "6:Juni",
                "7:Juli",
                "8:August",
                "9:September",
                "10:Oktober",
                "11:November",
                "12:Dezember"

            )
        } else {
            emptyList() // Return an empty list for non-German languages
        }
    }
}


