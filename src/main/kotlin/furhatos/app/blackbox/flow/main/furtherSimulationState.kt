package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No

var SimState:State = state(Parent) {
    onEntry {
        // Hier alle logVariablen schreiben !!

        delay(1000)
        furhat.say("Dieser zustand dient zur weiteren Simulation")
        if (userPflege) {
            goto(SimPflegeState)
        } else {
            goto(SimUserState)
        }
    }
}

var SimPflegeState:State = state(Parent) {
    onEntry {
        furhat.ask("Wollen sie die Simulation als Nutzer weiterführen ?")
    }
    onResponse<Yes> {
        resetPflegeVariables()
        goto(FakeStateUser)
    }
    onResponse<No> {
        furhat.say("Ok auf wiedersehen")
        exit()

    }
}

var SimUserState:State = state(Parent){
    onEntry {
        furhat.ask("Wollen sie die Simulation vortsetzen ?")
    }
    onResponse<Yes> {
        goto(SimTimeChangeState)
    }

    onResponse<No>{
        furhat.say("Ok auf Wiedersehen")
        exit()
    }
}


var SimTimeChangeState: State = state(Parent){
    onEntry {
        furhat.ask("Wollen sie die Uhrzeit ändern und das datum beibehalten ?")
    }
    onResponse<Yes> {
        goto(FakeStateTime)
    }
    onResponse<No> {
        goto(SimDateChangeState)

    }

}

var SimDateChangeState: State = state(Parent){
    onEntry {
        furhat.ask("Wollen sie das Datum ändern ?")
    }
    onResponse<Yes> {
        resetNightStateVariables()
        resetMorningStateVariables()

        goto(FakeStateDate)
    }
    onResponse<No> {
        goto(SimChangeUserState)
    }
}

var SimChangeUserState:State = state(Parent){
    onEntry {
        furhat.ask("Wollen sie den Benutzer zu einer Plegekraft wechseln ?")
    }
        onResponse<Yes> {
            goto(FakeStateUser)
        }
    onResponse<No> {
        furhat.say("Dann kann ich ihnen nicht weiter helfen. Auf widersehen")
        exit()

    }
}



