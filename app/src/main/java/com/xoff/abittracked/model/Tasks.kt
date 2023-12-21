package com.xoff.abittracked.model

import com.google.gson.annotations.SerializedName

data class Tasks( var title:String,
                  @SerializedName("tasks")
                  var tasks:List<TaskItem>) {

}