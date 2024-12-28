package furhatos.app.blackbox.flow

import furhatos.app.blackbox.flow.main.*
import furhatos.app.blackbox.setting.DISTANCE_TO_ENGAGE
import furhatos.app.blackbox.setting.MAX_NUMBER_OF_USERS
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.util.Gender
import furhatos.util.Language


val Init: State = state {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(DISTANCE_TO_ENGAGE, MAX_NUMBER_OF_USERS)
        furhat.setVoice(language = Language.GERMAN, gender = Gender.MALE)
        furhat.setInputLanguage(Language.GERMAN)
    }
    onEntry {
        /** start interaction */
        when {
            furhat.isVirtual() -> goto(FakeStateUser) // Convenient to bypass the need for user when running Virtual Furhat
            users.hasAny() -> {
                furhat.attend(users.random)
                goto(FakeStateDate)
            }
            else -> goto(Idle)
        }
    }

}
