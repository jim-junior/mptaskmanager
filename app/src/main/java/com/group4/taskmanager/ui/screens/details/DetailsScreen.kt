package com.group4.taskmanager.ui.screens.details

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.group4.taskmanager.ui.screens.home.Task


@Composable
fun DetailsScreen(
    task: Task,
    changeTaskStatus: (Boolean) -> Unit,
    onBackClick: () -> Unit = { }
) {
    val context = LocalContext.current

    Log.d("DetailsScreen", "Task: ${task.files}")

    val contentResolver = context.contentResolver
    val openFileInOtherApp = { location: String ->
        val uri = Uri.parse(location)
        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, contentResolver.getType(uri))
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)
        } else {
            Log.d(null, "Failed to open file")
        }

    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(onClick = {
                onBackClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
                
            }
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                //modifier = Modifier.padding(8.dp)
            )

            Checkbox(checked = task.completed, onCheckedChange = {
                changeTaskStatus(it)
            })
        }

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = task.startdate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Light,
                fontSize = 2.em
            )
            Text(
                text = task.duedate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Light,
                fontSize = 2.em
            )
        }

        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )

        HorizontalDivider()
        Text(
            text = "Sub Tasks",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Column {
                task.subtasks.forEach {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        HorizontalDivider()
        Text(
            text = "Files",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            Column {
                task.files.forEach {
                    Row {
                        Text(
                            text = it["name"].toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                        TextButton(
                            onClick = {
                                openFileInOtherApp(it["fileurl"].toString())
                            },
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "Open",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                    }
                }
            }
        }


    }

}

