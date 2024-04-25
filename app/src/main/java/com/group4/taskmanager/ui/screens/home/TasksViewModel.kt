package com.group4.taskmanager.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.group4.taskmanager.ui.FileObject
import java.text.SimpleDateFormat

class TasksViewModel: ViewModel() {

    var tasks by
        mutableStateOf(
            listOf<Task>())
        private set




    val db = Firebase.firestore



    fun fetchTasks() {
        db.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                var tasksList = mutableListOf<Task>()
                for (document in result) {
                    println("${document.id} => ${document.data}")
                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                    val st = document.data["startdate"] as Timestamp
                    val ed = document.data["duedate"] as Timestamp
                    val task = Task(
                        id = document.id,
                        title = document.data["name"].toString(),
                        description = document.data["description"].toString(),
                        duedate = ed.toDate().toString().substring(0, 10),
                        startdate = st.toDate().toString().substring(0, 10),
                        subtasks = document.data["subtasks"] as List<String>,
                        files = document.data["files"] as List<HashMap<String, String>>,
                        completed = document.data["completed"] as Boolean
                    )
                    tasksList.add(task)
                }
                tasks = tasksList
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }


    fun createTask(
        text: String,
        startDate: String,
        endDate: String,
        description: String,
        subTasks: List<String>,
        files: List<FileObject>,
        setLoading: (Boolean) -> Unit
    ) {
        Log.d(null, "Creating Task")

        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
        val sd = dateFormat.parse(endDate).time
        //val endDateTimeStamp = DateFormat.getDateInstance().parse(endDate)?.time
        Log.d(null, "End Date: $sd")
        setLoading(true)

        // Create a new task with a document ID
        val task = hashMapOf(
            "name" to text,
            "startdate" to startDate,
            "completed" to false,
            "description" to description,
            "duedate" to sd,
            "subtasks" to subTasks,
            "files" to files.map {
                hashMapOf(
                    "name" to it.name,
                    "fileurl" to it.fileUrl.toString()
                )
            }
        )

        // Add a new document with a generated ID
        db.collection("tasks")
            .add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(null, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(null, "Error adding document", e)
            }
            .addOnCompleteListener {
                setLoading(false)
            }


    }


    fun markTaskAsCompleted(taskId: String) {
        db.collection("tasks").document(taskId)
            .update("completed", true)
            .addOnSuccessListener {
                Log.d(null, "DocumentSnapshot successfully updated!")
                fetchTasks()
            }
            .addOnFailureListener { e -> Log.w(null, "Error updating document", e) }
    }
}