package com.example.cfs.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cfs.R
import com.example.cfs.models.Feedback
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = ListViewModel()
) {
    val feedbacks = viewModel.feedbackList.collectAsState(initial = listOf()).value
    val courseCodes = viewModel.courseCodeList.collectAsState(initial = listOf()).value
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {

            items(feedbacks.size) { index ->
                // Access the feedback object using the index
                val feedback = feedbacks[index]

                // Render the UI for each feedback item
                FeedbackItem(
                    feedback,
                    courseCode = courseCodes.first { it.id == feedback.course_id }.courseCode
                )
            }


        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedbackItem(
    feedback: Feedback,
    modifier: Modifier = Modifier,
    courseCode: String
) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .height(IntrinsicSize.Min), //intrinsic measurements
            verticalAlignment = Alignment.CenterVertically

        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
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

            Column(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
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


@Composable
@Preview(showBackground = true)
fun ListScreenPrv(modifier: Modifier = Modifier) {

}
