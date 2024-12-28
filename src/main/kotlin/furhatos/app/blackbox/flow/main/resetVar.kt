package furhatos.app.blackbox.flow.main

fun resetPflegeVariables() {
    logPantoRefill = false
    logMetaRefill = false
    logASSRefill = false
    logDoxaRefill = false
    logSimRefill = false
    userPflege = false
}

fun resetMorningStateVariables() {
    logNüchtern = false
    logPanto = false
    logPantoMenge = false
    logPantoUnsicher = false
    logMeta = false
    logMetaMenge = false
    logWasser = false
    logMetaUnsicher = false
    logASS = false
    logASSMenge = false
    logUnzerkaut = false
    logASS100Unsicher = false
}

fun resetNightStateVariables() {
    logDox = false
    logDoxMenge = false
    logDoxUnsicher = false
    logSim = false
    logSimMenge = false
    logSimUnsicher = false
    logGrapefruit = false
}