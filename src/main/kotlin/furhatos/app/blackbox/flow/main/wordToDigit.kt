package furhatos.app.blackbox.flow.main





val numberWords = mapOf(
    "eins" to "1", "zwei" to "2", "drei" to "3", "vier" to "4", "fuenf" to "5",
    "sechs" to "6", "sieben" to "7", "acht" to "8", "neun" to "9", "null" to "0",
    "zehn" to "10", "elf" to "11", "zwoelf" to "12", "dreizehn" to "13", "vierzehn" to "14",
    "fuenfzehn" to "15", "sechzehn" to "16", "siebzehn" to "17", "achtzehn" to "18", "neunzehn" to "19",
    "zwanzig" to "20", "einundzwanzig" to "21", "zweiundzwanzig" to "22", "dreiundzwanzig" to "23"
)


// Function to preprocess and convert number words to digits
fun convertWordsToDigits(input: String): String {
    val words = input.trim().lowercase().split(" ")
    return words.map { numberWords[it] ?: it }.joinToString("")
}