package com.example.cfs.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cfs.R
import com.example.cfs.models.Feedback
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<ListViewModel>()
    val feedbacks = viewModel.feedbackList.collectAsState(initial = listOf()).value
    val courseCodes = viewModel.courseCodeList.collectAsState(initial = listOf()).value
    var isLoading = viewModel.isLoading

    var selectedCard by remember { mutableStateOf<Feedback?>(null) }
    if (!isLoading) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                feedbacks.sortedBy { feedback -> courseCodes.first { it.id == feedback.course_id }.courseCode }
                items(feedbacks.size) { index ->
                    // Access the feedback object using the index
                    val feedback = feedbacks[index]


                    // Render the UI for each feedback item
                    FeedbackItem(
                        feedback = feedback,
                        courseCode = courseCodes.first { it.id == feedback.course_id }.courseCode
                    ) { clickedCard ->
                        selectedCard = clickedCard
                    }
                }

            }
            selectedCard?.let {
                InfoPopup(
                    feedback = it,
                    courseCode = courseCodes.first { courses -> it.course_id == courses.id }.courseCode
                ) {
                    selectedCard = null // Dismiss the popup

                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedbackItem(
    feedback: Feedback,
    modifier: Modifier = Modifier,
    courseCode: String,
    onClick: (Feedback) -> Unit
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .clickable { onClick(feedback) }
    ) {
        Row(
            Modifier
                .height(IntrinsicSize.Min), //intrinsic measurements
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1.3F)
            ) {
                Text(
                    text = courseCode,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )

            }
            Divider(

                modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(2.dp),
                color = MaterialTheme.colorScheme.background

            )

            Column(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .weight(4f)
            ) {
                Text(
                    text = feedback.course_topic,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = feedback.course_date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                        .toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoPopup(feedback: Feedback, courseCode: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = feedback.course_topic, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = courseCode,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier

                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = feedback.course_date.format(DateTimeFormatter.ofPattern("dd/MM/yy"))
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier

                    )

                }
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), // Customize color if needed
                    thickness = 1.dp // Customize thickness if needed
                )
                Text(
                    text = feedback.summary ?: "Feedback not available yet",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ListScreenPrv(modifier: Modifier = Modifier) {

}
