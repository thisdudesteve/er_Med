package furhatos.app.blackbox

import furhatos.app.blackbox.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class BlackboxSkill : Skill() {
    override fun start() {

        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
