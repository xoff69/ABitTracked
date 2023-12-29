package com.xoff.abittracked.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
/*
val expectedAttendees: Int,
                    var periodicity:Int,
    var nbhit:Int, var tsStartDate:Int,var autoRenew:Boolean, var archived:Boolean
 */
fun TaskCard(name: String, id: Int, description:String) {
    Card(
        modifier = Modifier
            // The space between each card and the other
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Column(Modifier.padding(8.dp)) {
                Text(
                    text = ""+id
                )
                Text(
                    text = name
                )
                Text(
                    text = description
                )

            }
        }
    }
}

