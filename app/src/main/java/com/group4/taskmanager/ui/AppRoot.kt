package com.group4.taskmanager.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.group4.taskmanager.ui.screens.home.HomeScreen


enum class TaskManagerScreen() {
    Home,
    TaskDetails
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController = rememberNavController()
) {



    // [START android_compose_layout_material_bottom_sheet2]
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                //colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                //    containerColor = MaterialTheme.colorScheme.primaryContainer,
                //    titleContentColor = MaterialTheme.colorScheme.primary,
                //),
                title = {
                    Text(
                        text = "Task Manager",
                        fontWeight = FontWeight.Bold,)
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerpadding ->

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {

                CreateTaskSheet()

            }
        }
        NavHost(
            navController = navController,
            startDestination = TaskManagerScreen.Home.name,
            modifier = Modifier.padding(innerpadding)
        ) {
            composable(route = TaskManagerScreen.Home.name) {

                val context = LocalContext.current
                HomeScreen()
            }
        }

    }
}


data class FileObject(
    val name: String,
    val fileUrl: Uri?
)


@Composable
fun CreateTaskSheet() {
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var text by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    var files by rememberSaveable { mutableStateOf(mutableListOf<FileObject>()) }
    var fileName by rememberSaveable {
        mutableStateOf("")
    }
    // subtasks: array of strings
    var subTaskName by rememberSaveable { mutableStateOf("") }
    var subTasks by rememberSaveable { mutableStateOf(mutableListOf<String>()) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        files.add(
            FileObject(
                name = fileName,
                fileUrl = it
            )
        )

        fileName = ""

    }

    val openInOtherApp = { uri: Uri? ->
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
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Create Task",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = { /*TODO*/ }) {
                Text("Create Task")

            }
        }




            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Task Name") },
                modifier = Modifier.padding(16.dp),
                supportingText = { Text("Enter the task name") }
            )


            TextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Start Date") },
                modifier = Modifier.padding(16.dp),
                supportingText = { Text("Enter the start date") }
            )

            TextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("End Date") },
                modifier = Modifier.padding(16.dp),
                supportingText = { Text("Enter the end date") }
            )

            HorizontalDivider()

            Text(
                text = "Subtasks",
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )

        TextField(
            value = subTaskName,
            onValueChange = { subTaskName = it },
            label = { Text("Subtask Name") },
            modifier = Modifier.padding(16.dp),
            supportingText = { Text("Enter the subtask name") }
        )



        TextButton(
            onClick = {
                subTasks.add(subTaskName)
                subTaskName = ""
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Add Subtask")
        }




        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            subTasks.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it)
                    TextButton(onClick = {
                        subTasks.remove(it)
                    }) {
                        Text("Remove")
                    }
                }
            }
        }







        HorizontalDivider()

        TextField(
            value = fileName,
            onValueChange = { fileName = it },
            label = { Text("File Name") },
            modifier = Modifier.padding(16.dp),
            supportingText = { Text("Give your file a name") }
        )

        TextButton(onClick = {
            launcher.launch(arrayOf("*/*"))
        }) {
            Text(text = "Upload File")
        }



        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            files.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(it.name)
                    TextButton(onClick = {
                        openInOtherApp(it.fileUrl)
                    }) {
                        Text("Open")
                    }
                    TextButton(onClick = {
                        files.remove(it)
                    }) {
                        Text("Remove")
                    }
                }
            }
        }


        HorizontalDivider()





    }


}


@Preview(showBackground = true)
@Composable
fun AppScaffoldPreview() {
    AppScaffold()
}
