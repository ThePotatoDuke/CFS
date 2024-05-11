package com.example.cfs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(modifier: Modifier = Modifier) {
    val courseItems = remember { mutableListOf("Course A", "Course B", "Course C") }


    var dateResult by remember { mutableStateOf("Pick a date") }
    var openDialog by remember { mutableStateOf(false) }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

//        TextField(
//            modifier = Modifier.clickable(enabled = true, onClick = { openDialog = true }),
//            value = dateResult,
//            onValueChange = {},
//            readOnly = true,
//            trailingIcon = {
//                IconButton(
//                    onClick = { openDialog = true }
//                ) {
//                    Icon(Icons.Rounded.DateRange, contentDescription = "Select Date")
//                }
//            },
//            colors = ExposedDropdownMenuDefaults.textFieldColors()
//        )
        //
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { openDialog = true }
        ) {
            Text(text = dateResult)
        }
        DropdownMenuComponent(
            isExpanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            selectedCourse = selectedCourse,
            onCourseSelected = { selectedCourse = it },
            courseItems = courseItems
        )


    }

    val datePickerState = rememberDatePickerState()
    DatePickerComponent(
        openDialog = openDialog,
        datePickerState = datePickerState,
        onDismissRequest = { openDialog = false },
        onDateSelected = { date ->
            dateResult = convertLongToTime(date)
            openDialog = false
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    openDialog: Boolean,
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                        onDateSelected(datePickerState.selectedDateMillis ?: 0L)
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

}

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MM/yy")
    return format.format(date)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedCourse: String,
    onCourseSelected: (String) -> Unit,
    courseItems: List<String>
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange(it) }
    ) {
        TextField(
            value = selectedCourse,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            courseItems.forEach { course ->
                DropdownMenuItem(
                    text = { Text(text = course) },
                    onClick = {
                        onCourseSelected(course)
                        onExpandedChange(false)
                    }
                )
            }
            // Add more items as needed
        }
    }
}

@Composable
@Preview(showBackground = true)
fun requestPreview() {
    RequestScreen()
}