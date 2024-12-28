package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.intents.DayEntity

fun convertSynToNum (day : DayEntity?):Int{
    val dayString = day.toString()
    val dayCase = dayString.lowercase()
    val daySynonyms = mapOf(
        "null" to 0,
        "eins" to 1, "erste" to 1, "erster" to 1, "ein" to 1, "1" to 1,
        "zwei" to 2, "zweiter" to 2, "zweite" to 2, "2" to 2,
        "drei" to 3, "dritter" to 3, "dritte" to 3, "3" to 3,
        "vier" to 4, "vierter" to 4, "vierte" to 4, "vierten" to 4, "4" to 4,
        "fünf" to 5, "fünfter" to 5, "fünfte" to 5, "5" to 5,
        "sechs" to 6, "sechster" to 6, "sechste" to 6, "6" to 6,
        "sieben" to 7, "siebter" to 7, "siebte" to 7, "7" to 7,
        "acht" to 8, "achter" to 8, "achte" to 8, "8" to 8,
        "neun" to 9, "neunter" to 9, "neunte" to 9, "9" to 9,
        "zehn" to 10, "zehnter" to 10, "zehnte" to 10, "10" to 10,
        "elf" to 11, "elfter" to 11, "elfte" to 11, "11" to 11,
        "zwölf" to 12, "zwölfter" to 12, "zwölfte" to 12, "12" to 12,
        "dreizehn" to 13, "dreizehnter" to 13, "dreizehnte" to 13, "13" to 13,
        "vierzehn" to 14, "vierzehnter" to 14, "vierzehnte" to 14, "14" to 14,
        "fünfzehn" to 15, "fünfzehnter" to 15, "fünfzehnte" to 15, "15" to 15,
        "sechzehn" to 16, "sechzehnter" to 16, "sechzehnte" to 16, "16" to 16,
        "siebzehn" to 17, "siebzehnter" to 17, "siebzehnte" to 17, "17" to 17,
        "achtzehn" to 18, "achtzehnter" to 18, "achtzehnte" to 18, "18" to 18,
        "neunzehn" to 19, "neunzehnter" to 19, "neunzehnte" to 19, "19" to 19,
        "zwanzig" to 20, "zwanzigster" to 20, "zwanzigste" to 20, "20" to 20,
        "einundzwanzig" to 21, "einundzwanzigster" to 21, "einundzwanzigste" to 21, "21" to 21,
        "zweiundzwanzig" to 22, "zweiundzwanzigster" to 22, "zweiundzwanzigste" to 22, "22" to 22,
        "dreiundzwanzig" to 23, "dreiundzwanzigster" to 23, "dreiundzwanzigste" to 23, "23" to 23,
        "vierundzwanzig" to 24, "vierundzwanzigster" to 24, "vierundzwanzigste" to 24, "24" to 24,
        "fünfundzwanzig" to 25, "fünfundzwanzigster" to 25, "fünfundzwanzigste" to 25, "25" to 25,
        "sechsundzwanzig" to 26, "sechsundzwanzigster" to 26, "sechsundzwanzigste" to 26, "26" to 26,
        "siebenundzwanzig" to 27, "siebenundzwanzigster" to 27, "siebenundzwanzigste" to 27, "27" to 27,
        "achtundzwanzig" to 28, "achtundzwanzigster" to 28, "achtundzwanzigste" to 28, "28" to 28,
        "neunundzwanzig" to 29, "neunundzwanzigster" to 29, "neunundzwanzigste" to 29, "29" to 29,
        "dreißig" to 30, "dreißigster" to 30, "dreißigste" to 30, "30" to 30,
        "einunddreißig" to 31, "einunddreißigster" to 31, "einunddreißigste" to 31, "31" to 31
        )



        return daySynonyms[dayCase] ?: -1
    }



