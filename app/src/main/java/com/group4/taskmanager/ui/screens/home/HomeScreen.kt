package com.group4.taskmanager.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


data class Task(
    val id: String,
    val title: String,
    val description: String,
    val duedate: String,
    val startdate: String,
    val subtasks: List<String>,
    val files: List<HashMap<
            String,
            String
            >>,
    val completed: Boolean = false
)


@Composable
fun HomeScreen(
    tasks: List<Task>,
    onTaskComplete: (Task, Boolean) -> Unit,
    onOpenTaskDetails: (Task) -> Unit,
    modifier: Modifier = Modifier
) {


    val context = LocalContext.current





    if (tasks.isEmpty()) {
        Text(
            text = "No tasks available",
            modifier = Modifier.padding(10.dp)
        )
    }





    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(tasks.size) { index ->
            TaskCard(
                task = tasks[index],
                changeTaskStatus = onTaskComplete,
                onTaskClick = onOpenTaskDetails
            )
        }
    }

}


@Composable
fun TaskCard(
    task : Task,
    changeTaskStatus: (Task, Boolean) -> Unit,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable {
                onTaskClick(task)
            }

        ,
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Checkbox(checked = task.completed, onCheckedChange = {
                changeTaskStatus(task, it)
            })

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {


                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        //fontSize = 7.em
                    )

                    if (task.completed) {
                        Text(
                            text = "Completed",
                            color = Color.Green,
                            //fontWeight = FontWeight.,
                            fontSize = 2.em
                        )
                    }



                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 5.dp,
                            end = 5.dp,
                            top = 0.dp,
                            bottom = 5.dp
                        )
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {


                    Text(
                        text = task.description,
                        //fontWeight = FontWeight.,
                        fontSize = 3.em
                    )
                    Text(
                        text = task.duedate,
                        //fontWeight = FontWeight.,
                        fontSize = 2.em
                    )

                }

            }

        }


    }



}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //HomeScreen()
}

