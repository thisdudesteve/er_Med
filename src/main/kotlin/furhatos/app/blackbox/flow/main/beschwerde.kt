package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

var logGibtBeschwerde : Boolean = false

val Beschwerde: State = state(Parent) {
    onEntry {
        furhat.ask("Ich grüße sie. Haben Sie irgendwelche Beschwerden?")
    }
    onResponse<No> {
        logGibtBeschwerde = false
        BlackboxLogger.log("[Patient] Hat keine Beschwerden.")
        furhat.say("Ok lassen sie uns fortfahren")
        val periode = getTimePeriode(hour)
        when (periode) {
            "Morgen" -> goto(MorningState)
            "Mittag" -> goto(MiddayState)
            "Abend" -> goto(EveningState)
            "Nacht" -> goto(NightState)

        }
    }
    onResponse<Yes> {
        logGibtBeschwerde = true
        goto(BeschwerdeIdentify)
        }
    }


val BeschwerdeIdentify : State = state(Parent) {
    onEntry {
        furhat.ask("Bitte erläutern sie ihre beschwerden")
        }
    onResponse {
        val beschwerden = it.text
        BlackboxLogger.log("[Patient] Hat folgende Beschwerden: $beschwerden")
        furhat.say("Bitte kontaktieren sie ihren Arzt, bevor wir mit der Medikation fortfahren.")
        exit()

    }
    }
