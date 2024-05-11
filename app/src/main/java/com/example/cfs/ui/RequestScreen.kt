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
    var dateResult by remember { mutableStateOf("Pick a date") }
    val openDialog = remember { mutableStateOf(false) }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { openDialog.value = true }
        ) {
            Text(text = dateResult)
        }
        DropdownMenuComponent(
            isExpanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            selectedCourse = selectedCourse,
            onCourseSelected = { selectedCourse = it }
        )


    }
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        DatePickerComponent(
            datePickerState = datePickerState,
            onDismissRequest = { openDialog.value = false },
            onDateSelected = { date ->
                dateResult = convertLongToTime(date)
                openDialog.value = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
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
    onCourseSelected: (String) -> Unit
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
            DropdownMenuItem(
                text = { Text(text = "seng 101") },
                onClick = {
                    onCourseSelected("seng 101")
                    onExpandedChange(false)
                }
            )
            // Add more items as needed
        }
    }
}

@Composable
@Preview(showBackground = true)
fun requestPreview() {
    RequestScreen()
}