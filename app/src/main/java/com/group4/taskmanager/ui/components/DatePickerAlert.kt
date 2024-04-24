package com.group4.taskmanager.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DatePickerDemo() {
    var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val context = LocalContext.current

    Column {
        Button(onClick = {
            showDatePickerDialog(context, selectedDate) { newDate ->
                selectedDate = newDate
            }
        }) {
            Text("Select Date")
        }
        Text(
            text = "Selected Date: ${selectedDate.get(Calendar.YEAR)}-${selectedDate.get(Calendar.MONTH) + 1}-${selectedDate.get(Calendar.DAY_OF_MONTH)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = androidx.compose.ui.Modifier.padding(16.dp)
        )
    }
}

fun showDatePickerDialog(context: android.content.Context, initialDate: Calendar, onDateSelected: (Calendar) -> Unit) {
    val datePicker = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            onDateSelected(selectedDate)
        },
        initialDate.get(Calendar.YEAR),
        initialDate.get(Calendar.MONTH),
        initialDate.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}

@Preview(showBackground = true)
@Composable
fun PreviewDatePickerDemo() {
    DatePickerDemo()
}
