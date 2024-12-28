package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*

val EveningState: State = state(Parent){
    onEntry {
        BlackboxLogger.log("[Patient] Prüfe Mittagsmedikation - keine Einnahmen vorgesehen")
        furhat.say("In ihrem aktuellen Medikamentenplan liegen keine Einnahmen am Abend vor.")
        goto(SimState)
    }
}