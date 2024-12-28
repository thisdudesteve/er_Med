// BlackboxLogger.kt
package furhatos.app.blackbox.flow.main

import java.io.File
import furhatos.app.blackbox.flow.Parent
import furhatos.app.blackbox.flow.intents.DayEntity
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


object TimeManager {
    private var baseTime: LocalTime? = null
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    init {
        // Diese init-Block wird nur einmal beim Erstellen des Objects ausgeführt
        val basierteZeit = if(logTime >= 10) {
            "${logTime}:00"
        } else {
            "0${logTime}:00"
        }
        baseTime = LocalTime.parse(basierteZeit)
    }

    fun getCurrentTime(): String {
        return baseTime?.format(formatter) ?: "00:00"
    }

    fun addMinutes(minutes: Int) {
        baseTime = baseTime?.plusMinutes(minutes.toLong())
    }

    fun resetTime(hour: Int) {
        val basierteZeit = if(hour >= 10) {
            "${hour}:00"
        } else {
            "0${hour}:00"
        }
        baseTime = LocalTime.parse(basierteZeit)
    }
}

// Beispiel für die Integration in den Logger
object BlackboxLogger {
    private val logFile = File("/Users/steve/Downloads/tutorials-main/RoboEthik-main/src/main/resources/blackboxLog.txt")

    fun log(message: String) {
        val currentTime = TimeManager.getCurrentTime()
        val timestamp = "$loggerDayDate.$logMonth.2024 $currentTime:00.000"
        logFile.appendText("$timestamp: $message\n")
        TimeManager.addMinutes(1) // Fügt standardmäßig 1 Minute hinzu
    }

    fun log(message: String, minutesToAdd: Int) {
        val currentTime = TimeManager.getCurrentTime()
        val timestamp = "$logDayDate.$logMonth.2024 $currentTime:00.000"
        logFile.appendText("$timestamp: $message\n")
        TimeManager.addMinutes(minutesToAdd)
    }
}

// Logging variables
var logNüchtern: Boolean = false
var logPanto: Boolean = false
var logPantoMenge: Boolean = false
var logPantoUnsicher: Boolean = false
var logMeta: Boolean = false
var logMetaMenge: Boolean = false
var logWasser: Boolean = false
var logMetaUnsicher: Boolean = false
var logASS: Boolean = false
var logASSMenge: Boolean = false
var logUnzerkaut: Boolean = false
var logASS100Unsicher: Boolean = false

// morningState.kt


val MorningState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Starte Morgenmedikation-Routine", 1)
        furhat.ask("Sie müssen 3 Medikamente einnehmen. Darunter Pantoprazol. Haben Sie heute schon etwas gegessen?")
    }
    onResponse<No> {
        logNüchtern = true
        BlackboxLogger.log("[Patient] Ist nüchtern: $logNüchtern")
        goto(PantoprazolState)
    }
    onResponse<Yes> {
        BlackboxLogger.log("[Patient] Hat bereits gegessen - Warnung ausgegeben", 1)
        furhat.say("Sie dürfen Pantoprazol nur auf nüchternen Magen einnehmen!")
        goto(MetaprololState)
    }
}

val PantoprazolState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Beginne Pantoprazol-Verabreichung",1)
        furhat.say("Bitte nehmen Sie 20 bis 40 Milligramm Pantoprazol ein.")
        delay(3000)
        furhat.ask("Haben Sie das Pantoprazol in der angegebenen Menge eingenommen?")
    }
    onResponse<Yes> {
        logPanto = true
        logPantoMenge = true
        BlackboxLogger.log("[Patient] Pantoprazol eingenommen: $logPanto, korrekte Menge: $logPantoMenge",1)
        goto(MetaprololState)
    }
    onResponse<No> {
        goto(PantoprazolWrongAmountState)
    }
}

val PantoprazolWrongAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche Pantoprazol-Menge",1)
        furhat.ask("Haben Sie Pantoprazol in falscher Menge eingenommen?")
    }
    onResponse<Yes> {
        logPanto = true
        BlackboxLogger.log("[Patient] Pantoprazol in falscher Menge eingenommen: $logPanto",1)
        goto(MetaprololState)
    }
    onResponse<No> {
        goto(PantoprazolNotTakenState)
    }
}

val PantoprazolNotTakenState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob Pantoprazol überhaupt eingenommen wurde",1)
        furhat.ask("Haben Sie Pantoprazol überhaupt eingenommen?")
    }
    onResponse<Yes> {
        logPantoUnsicher = true
        BlackboxLogger.log("[Patient] Pantoprazol-Status unklar: $logPantoUnsicher",1)
        furhat.say("Hier ist wohl irgendetwas schiefgelaufen.")
        goto(MetaprololState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Patient] Pantoprazol nicht eingenommen",1)
        goto(MetaprololState)
    }
}

val MetaprololState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Beginne Metaprolol-Verabreichung",1)
        furhat.say("Als nächstes 50-200mg Metaprolol. Bitte nehmen Sie das Medikament zusammen mit Wasser ein.")
        delay(3000)
        furhat.ask("Haben Sie das Medikament in der angegebenen Menge und mit Wasser eingenommen?")
    }
    onResponse<Yes> {
        logMeta = true
        logWasser = true
        logMetaMenge = true
        BlackboxLogger.log("[Patient] Metaprolol eingenommen: $logMeta, mit Wasser: $logWasser, korrekte Menge: $logMetaMenge",1)
        goto(ASS100State)
    }
    onResponse<No> {
        goto(MetaprololWithoutWaterState)
    }
}

val MetaprololWithoutWaterState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe Metaprolol ohne Wasser",1)
        furhat.ask("Haben Sie das Medikament ohne Wasser aber in richtiger Menge eingenommen?")
    }
    onResponse<Yes> {
        logMeta = true
        logMetaMenge = true
        BlackboxLogger.log("[Patient] Metaprolol ohne Wasser eingenommen: $logMeta, korrekte Menge: $logMetaMenge",1)
        goto(ASS100State)
    }
    onResponse<No> {
        goto(MetaprololWrongAmountState)
    }
}

val MetaprololWrongAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche Metaprolol-Menge",1)
        furhat.ask("Haben Sie das Medikament in falscher Menge eingenommen?")
    }
    onResponse<Yes> {
        logMeta = true
        logMetaMenge = false
        BlackboxLogger.log("[Patient] Metaprolol in falscher Menge eingenommen: $logMeta",1)
        goto(ASS100State)
    }
    onResponse<No> {
        goto(MetaprololNotTakenState)
    }
}

val MetaprololNotTakenState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob Metaprolol überhaupt eingenommen wurde",1)
        furhat.ask("Haben Sie das Medikament überhaupt eingenommen?")
    }
    onResponse<Yes> {
        logMetaUnsicher = true
        BlackboxLogger.log("[Patient] Metaprolol-Status unklar: $logMetaUnsicher",1)
        furhat.say("Hier ist wohl etwas schiefgelaufen.")
        goto(ASS100State)
    }
    onResponse<No> {
        BlackboxLogger.log("[Patient] Metaprolol nicht eingenommen")
        goto(ASS100State)
    }
}

val ASS100State: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Beginne ASS100-Verabreichung",1)
        furhat.say("Dann noch 100 Milligramm A S S 100. Bitte unzerkaut einnehmen.")
        delay(3000)
        furhat.ask("Haben Sie A S S 100 in der richtigen Menge unzerkaut eingenommen?")
    }
    onResponse<Yes> {
        logUnzerkaut = true
        logASS = true
        logASSMenge = true
        BlackboxLogger.log("[Patient] ASS100 eingenommen: $logASS, unzerkaut: $logUnzerkaut, korrekte Menge: $logASSMenge",1)
        goto(SimState)
    }
    onResponse<No> {
        goto(ASS100ZerkautState)
    }
}

val ASS100ZerkautState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob ASS100 zerkaut wurde",1)
        furhat.ask("Haben Sie A S S 100 in 100 Milligramm zerkaut eingenommen?")
    }
    onResponse<Yes> {
        logASS = true
        logASSMenge = true
        BlackboxLogger.log("[Patient] ASS100 zerkaut eingenommen: $logASS, korrekte Menge: $logASSMenge",1)
        goto(SimState)
    }
    onResponse<No> {
        goto(ASS100WrongAmountState)
    }
}

val ASS100WrongAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche ASS100-Menge",1)
        furhat.ask("Haben Sie A S S 100 in falscher Menge unzerkaut eingenommen?")
    }
    onResponse<Yes> {
        logASS = true
        BlackboxLogger.log("[Patient] ASS100 in falscher Menge eingenommen: $logASS",1)
        goto(SimState)
    }
    onResponse<No> {
        goto(ASS100NotTakenState)
    }
}

val ASS100NotTakenState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob ASS100 überhaupt eingenommen wurde",1)
        furhat.ask("Haben Sie A S S 100 überhaupt eingenommen?")
    }
    onResponse<Yes> {
        logASS100Unsicher = true
        BlackboxLogger.log("[Patient] ASS100-Status unklar: $logASS100Unsicher",1)
        furhat.say("Irgendetwas ist hier wohl schiefgelaufen.")
        goto(SimState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Patient] ASS100 nicht eingenommen",1)
        goto(SimState)
    }
}