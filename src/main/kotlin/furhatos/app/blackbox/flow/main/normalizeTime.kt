package furhatos.app.blackbox.flow.main


fun normalizeTimeInput(input: String): String {
    return input
        .replace(":", " Uhr ") // Normalize colons
        .replace(",", " Uhr ") // Normalize commas
        .replace(".", " Uhr ") // Normalize dots
        .replace("\\s+".toRegex(), " ") // Remove extra spaces
        .trim()
}