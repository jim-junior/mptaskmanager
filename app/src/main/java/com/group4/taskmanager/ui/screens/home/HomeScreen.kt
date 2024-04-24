package com.group4.taskmanager.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
    ) {
        item {
            TaskCard()
        }
        item  {
            TaskCard()
        }
        item  {
            TaskCard()
        }
        item  {
            TaskCard()
        }
    }


}


@Composable
fun TaskCard(
    modifier: Modifier = Modifier
) {
    var ischecked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            //.background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth(),
            //.clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            //.border(BorderStroke(2.dp, SolidColor(Color.Red))),
shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(checked = ischecked, onCheckedChange = { ischecked = it })

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {


                    Text(
                        text = "Read Books",
                        fontWeight = FontWeight.Bold,
                        //fontSize = 7.em
                    )



                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {


                    Text(
                        text = "Task Description",
                        fontWeight = FontWeight.Medium,
                        //fontSize = 7.em
                    )
                    Text(
                        text = "20/04/2024",
                        fontWeight = FontWeight.Light,
                        //fontSize = 7.em
                    )

                }

            }

        }


    }



}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

