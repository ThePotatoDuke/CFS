package com.example.cfs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cfs.R
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestViewModel = RequestViewModel()
) {
    val courseCodes = viewModel.courseCodeList.collectAsState(initial = listOf()).value

    var dateResult = viewModel.dateResult.collectAsState(initial = "Pick a date").value

    var isDateFocused = viewModel.isDateFocused.collectAsState(initial = false).value

    var isExpanded = viewModel.isExpanded.collectAsState(initial = false).value
    var selectedCourse = viewModel.selectedCourse.collectAsState(initial = "Courses").value

    var topic = viewModel.topic.collectAsState(initial = "").value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(

            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(50.dp)
            ) {
                DropdownMenuComponent(
                    isExpanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    selectedCourse = selectedCourse,
                    onCourseSelected = { selectedCourse = it },
                    courseItems = courseCodes,
                )

                TextField(
                    value = dateResult.toString(),
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Date") }, // Add label here from resource
                    trailingIcon = {
                        IconButton(
                            onClick = { isDateFocused = true }
                        ) {
                            Icon(Icons.Rounded.DateRange, contentDescription = "Select Date")
                        }
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isDateFocused = it.isFocused }
                )


                val datePickerState = rememberDatePickerState()
                DatePickerComponent(
                    isFocused = isDateFocused,
                    datePickerState = datePickerState,
                    onDismissRequest = { isDateFocused = false },
                    onDateSelected = { date ->
                        dateResult = convertLongToTime(date)
                        isDateFocused = false
                    },
                )

                TextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("Topic") }, // Add label here
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    isFocused: Boolean,
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    if (isFocused) {
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