package furhatos.app.blackbox.flow.main

import furhatos.app.blackbox.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting: State = state(Parent) {
    onEntry {
        furhat.ask("")
    }

    onResponse<Yes> {
        furhat.say("Hello World! ")
    }

    onResponse<No> {
        furhat.say("Ok.")
    }

}

