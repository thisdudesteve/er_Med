package furhatos.app.blackbox.flow.main

import javax.swing.plaf.nimbus.State

fun getTimePeriode(hour:Int):String{
    return when (hour){
        in 6..11 -> "Morgen"
        in 12..17 -> "Mittag"
        in 18..21 -> "Abend"
        in 22..24 -> "Nacht"
        in 0..5 -> "Nacht"
        else -> "Ungültige Zeit"
    }

}