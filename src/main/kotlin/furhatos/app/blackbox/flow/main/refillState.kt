package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No

var logPantoRefill: Boolean = false
var logMetaRefill: Boolean = false
var logASSRefill: Boolean = false
var logDoxaRefill: Boolean = false
var logSimRefill: Boolean = false

val RefillState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Beginn der Medikamenten-Auffüllung")
        furhat.ask("Wollen sie die Medikamente wieder Auffüllen ?")
    }
    onResponse<Yes> {
        BlackboxLogger.log("[Pflegekraft] Auffüllung zugestimmt")
        goto(RefillPantoState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] Auffüllung abgelehnt")
        furhat.say("Ok verstanden. Auf wiedersehen.")
        goto(SimState)
    }
}

val RefillPantoState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Aufforderung Pantoprazol aufzufüllen")
        furhat.say("Ok bitte füllen sie jetz die Pantoprazol für die kommende Woche wieder auf ")
        Thread.sleep(3000)
        furhat.ask("Haben sie Pantoprazol wieder aufgefüllt ?")
    }
    onResponse<Yes> {
        logPantoRefill = true
        BlackboxLogger.log("[Pflegekraft] Pantoprazol aufgefüllt: $logPantoRefill")
        goto(RefillMetaState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] Pantoprazol nicht aufgefüllt")
        goto(RefillMetaState)
    }
}

val RefillMetaState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Aufforderung Metaprolol aufzufüllen")
        furhat.say("Ok bitte füllen sie jetz die Metaprolol für die kommende Woche wieder auf ")
        Thread.sleep(3000)
        furhat.ask("Haben sie Metaprolol wieder aufgefüllt ?")
    }
    onResponse<Yes> {
        logMetaRefill = true
        BlackboxLogger.log("[Pflegekraft] Metaprolol aufgefüllt: $logMetaRefill")
        goto(RefillASS100State)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] Metaprolol nicht aufgefüllt")
        goto(RefillASS100State)
    }
}

val RefillASS100State: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Aufforderung ASS100 aufzufüllen")
        furhat.say("Ok bitte füllen sie jetz A S S 100 für die kommende Woche wieder auf ")
        Thread.sleep(3000)
        furhat.ask("Haben sie A S S 100 wieder aufgefüllt ?")
    }
    onResponse<Yes> {
        logASSRefill = true
        BlackboxLogger.log("[Pflegekraft] ASS100 aufgefüllt: $logASSRefill")
        goto(RefillDoxaState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] ASS100 nicht aufgefüllt")
        goto(RefillDoxaState)
    }
}

val RefillDoxaState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Aufforderung Doxazosin aufzufüllen")
        furhat.say("Ok bitte füllen sie Doxazosin für die kommende Woche wieder auf ")
        Thread.sleep(3000)
        furhat.ask("Haben sie Doxazosin wieder aufgefüllt ?")
    }
    onResponse<Yes> {
        logDoxaRefill = true
        BlackboxLogger.log("[Pflegekraft] Doxazosin aufgefüllt: $logDoxaRefill")
        goto(RefillSimState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] Doxazosin nicht aufgefüllt")
        goto(RefillSimState)
    }
}

val RefillSimState: State = state(Parent) {
    onEntry {
        BlackboxLogger.log("[Pflegekraft] Aufforderung Simvastatin aufzufüllen")
        furhat.say("Ok bitte füllen sie Simvastatin für die kommende Woche wieder auf ")
        Thread.sleep(3000)
        furhat.ask("Haben sie Simvastatin wieder aufgefüllt ?")
    }
    onResponse<Yes> {
        logSimRefill = true
        BlackboxLogger.log("[Pflegekraft] Simvastatin aufgefüllt: $logSimRefill")
        furhat.say("Das war alles, wir sehen uns in der nächsten Woche")
        goto(SimState)
    }
    onResponse<No> {
        BlackboxLogger.log("[Pflegekraft] Simvastatin nicht aufgefüllt")
        furhat.say("Danke. wir sehen uns in der nächsten Woche ")
        goto(SimState)
    }
}