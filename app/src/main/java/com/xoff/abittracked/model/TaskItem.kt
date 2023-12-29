package com.xoff.abittracked.model

data class TaskItem(var id: Int,var name:String,val expectedAttendees: Int,
                    var periodicity:Int,var description:String,
    var nbhit:Int, var tsStartDate:Int,var autoRenew:Boolean, var archived:Boolean ) {

}
