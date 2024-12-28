package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

// Logging variables
var logDox: Boolean = false
var logDoxMenge: Boolean = false
var logDoxUnsicher: Boolean = false
var logSim: Boolean = false
var logSimMenge: Boolean = false
var logSimUnsicher: Boolean = false
var logGrapefruit: Boolean = false

val NightState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Starte Abendmedikation-Routine")
        furhat.say("Sie müssen die Medikamente Doxazosin und Simvastatin einnehmen.")
        goto(DoxazosinState)
    }
}

val DoxazosinState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Beginne Doxazosin-Verabreichung")
        furhat.say("Bitte nehmen Sie eine Tablette Doxazosin ein.")
        delay(3000)
        furhat.ask("Haben Sie eine Tablette Doxazosin eingenommen?")
    }
    onResponse<Yes> {
        logDox = true
        logDoxMenge = true
        BlackboxLogger.log("[Patient] Doxazosin eingenommen: $logDox, korrekte Menge: $logDoxMenge")
        goto(SimvastatinState)
    }
    onResponse<No> {
        goto(DoxazosinWrongAmountState)
    }
}

val DoxazosinWrongAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche Doxazosin-Menge")
        furhat.ask("Haben Sie das Doxazosin in falscher Menge eingenommen?")
    }
    onResponse<Yes> {
        logDox = true
        BlackboxLogger.log("[Patient] Doxazosin in falscher Menge eingenommen: $logDox")
        goto(SimvastatinState)
    }
    onResponse<No> {
        goto(DoxazosinNotTakenState)
    }
}

val DoxazosinNotTakenState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob Doxazosin überhaupt eingenommen wurde")
        furhat.ask("Haben Sie das Medikament überhaupt eingenommen?")
    }
    onResponse<Yes> {
        furhat.say("Hier ist wohl irgendetwas schiefgelaufen.")
        logDoxUnsicher = true
        BlackboxLogger.log("[Patient] Doxazosin-Status unklar: $logDoxUnsicher")
        goto(SimvastatinState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Patient] Doxazosin nicht eingenommen")
        goto(SimvastatinState)
    }
}

val SimvastatinState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Beginne Simvastatin-Verabreichung")
        furhat.say("Jetzt müssen Sie noch 10 bis 40 Milligramm Simvastatin einnehmen. Aber bitte nicht mit Grapefruitsaft.")
        delay(3000)
        furhat.ask("Haben Sie das Simvastatin in richtiger Menge und ohne Grapefruitsaft eingenommen?")
    }
    onResponse<Yes> {
        logSim = true
        logSimMenge = true
        BlackboxLogger.log("[Patient] Simvastatin eingenommen: $logSim, korrekte Menge: $logSimMenge, mit Grapefruit: false")
        goto(SimState)
    }
    onResponse<No> {
        goto(SimvastatinWrongAmountState)
    }
}

val SimvastatinWrongAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe Simvastatin mit Grapefruit")
        furhat.ask("Haben Sie Simvastatin in richtiger Menge, aber mit Grapefruitsaft eingenommen?")
    }
    onResponse<Yes> {
        logSim = true
        logSimMenge = true
        logGrapefruit = true
        BlackboxLogger.log("[Patient] Simvastatin eingenommen: $logSim, korrekte Menge: $logSimMenge, mit Grapefruit: $logGrapefruit")
        goto(SimState)
    }
    onResponse<No> {
        goto(SimvastatinIncorrectAmountState)
    }
}

val SimvastatinIncorrectAmountState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche Simvastatin-Menge")
        furhat.ask("Haben Sie Simvastatin richtig, aber in falscher Menge eingenommen?")
    }
    onResponse<Yes> {
        logSim = true
        BlackboxLogger.log("[Patient] Simvastatin in falscher Menge eingenommen: $logSim, mit Grapefruit: false")
        goto(SimState)
    }
    onResponse<No> {
        goto(SimvastatinIncorrectWithGrapefruitState)
    }
}

val SimvastatinIncorrectWithGrapefruitState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe falsche Simvastatin-Menge mit Grapefruit")
        furhat.ask("Haben Sie Simvastatin in falscher Menge mit Grapefruitsaft eingenommen?")
    }
    onResponse<Yes> {
        logSim = true
        logGrapefruit = true
        BlackboxLogger.log("[Patient] Simvastatin in falscher Menge eingenommen: $logSim, mit Grapefruit: $logGrapefruit")
        goto(SimState)
    }
    onResponse<No> {
        goto(SimvastatinNotTakenState)
    }
}

val SimvastatinNotTakenState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe ob Simvastatin überhaupt eingenommen wurde")
        furhat.ask("Haben Sie das Medikament überhaupt eingenommen?")
    }
    onResponse<Yes> {
        logSimUnsicher = true
        BlackboxLogger.log("[Patient] Simvastatin-Status unklar: $logSimUnsicher")
        furhat.say("Hier ist wohl etwas schiefgelaufen.")
        goto(SimState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Patient] Simvastatin nicht eingenommen")
        goto(SimState)
    }
}