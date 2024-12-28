package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.app.blackbox.flow.intents.DateIntent
import furhatos.app.blackbox.flow.intents.DayEntity
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


var userPflege: Boolean = false
var logWeekDay: String =""
var logDayDate: String =""
var loggerDayDate : DayEntity? = null
var logMonth: String = ""
var logTime: Int = 0
var hour :Int = 0

val FakeStateUser: State = state(Parent) {

    onEntry {
        furhat.say("Das folgende dient zur Simulation")
        delay(1000)
        furhat.ask("Sind sie Eine Pflegekraft ?")
    }

    onResponse<Yes> {
        goto(FakeStateCode)
        }
        onResponse<No>{
            furhat.say("Ok verstehe.")
            goto(FakeStateDate)
        }
}



val FakeStateCode: State = state(Parent) {
    var attempts = 0
    val maxAttempts = 3
    val correctCode = "123"

    onEntry {
        furhat.ask("3-Stelligen Sicherheitscode bitte.")
    }

    onResponse {
        val response = it.text.trim()
        val processedResponse = convertWordsToDigits(response)
        if (processedResponse.matches("\\d{3}".toRegex())) {
            if (processedResponse == correctCode) {
                furhat.say("Zugang gewährt")
                userPflege = true
                goto(FakeStateDate)
            } else {
                attempts++
                if (attempts < maxAttempts) {
                    furhat.say("Falscher code. Sie haben noch ${maxAttempts - attempts} Versuche.")
                    reentry()
                } else {
                    furhat.say("Zugang verwehrt..")

                }
            }
        } else {
            furhat.say("Bitte nur 3 Stellig.")
            reentry()
        }
    }
}








val FakeStateDate: State = state(Parent) {
    onEntry {
        furhat.ask("Bitte geben sie mir ein datum in der Form erster Januar")
    }
    onResponse<DateIntent> {
        if ( it.intent.day != null && it.intent.month != null ) {
            furhat.say("Es ist also ${getWeekDay(convertSynToNum(it.intent.day),it.intent.month,2024)} der ${it.intent.day} ${it.intent.month} 2024")
            print("Datum ${it.intent.day} ${it.intent.month} 2024")
            logWeekDay = getWeekDay(convertSynToNum(it.intent.day),it.intent.month,2024)
            logDayDate = it.intent.day.toString()
            loggerDayDate = it.intent.day
            logMonth = it.intent.month.toString()
        }else{
            furhat.say("Bitte widerholen sie")
            reentry()
        }
        goto(FakeStateTime)
    }
}

val FakeStateTime: State = state(Parent) {
    onEntry {
        furhat.ask("Bitte geben Sie mir eine Uhrzeit, in ganzen Stunden.")
    }

    onResponse {
        val rawResponse = it.text.trim()
        val normalizedResponse = normalizeTimeInput(rawResponse)

        // Regex to match various time formats
        val timeRegex = "(\\d{1,2})\\s*[Uhr:,.]*\\s*(\\d{1,2})?".toRegex()
        val matchResult = timeRegex.matchEntire(normalizedResponse)

        if (matchResult != null) {
            hour = matchResult.groupValues[1].toInt()

            if (hour in 0..23 ) {
                furhat.say("Die Uhrzeit ist: $hour Uhr.")
                logTime = hour.toString().toInt()
                if(userPflege){
                    goto(RefillState)
                }else{
                    goto(Beschwerde)

                }
            } else {
                furhat.say("Ungültige Uhrzeit. Bitte geben Sie eine gültige Uhrzeit ein.")
                reentry()
            }
        } else {
            furhat.say("Ich konnte die Uhrzeit nicht verstehen. Bitte versuchen Sie es erneut.")
            reentry()
        }
    }
}




