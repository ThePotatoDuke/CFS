package com.example.cfs.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.cfs.R
import com.example.cfs.data.supabase
import com.example.cfs.models.Feedback
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel
) {
    val feedbacks = viewModel.feedbackList.collectAsState(initial = listOf()).value
//    LazyColumn(modifier = Modifier.fillMaxHeight()) {
//        feedbacks?.size?.let {
//            items(it) { index ->
//                // Access the feedback object using the index
//                val feedback = feedbacks[index]
//
//                // Render the UI for each feedback item
//                FeedbackItem(feedback, courseCode = viewModel.getCourseCode(feedback.course_id))
//            }
//        }
//
//    }
//    LazyColumn {
//        items(
//            items = feedbacks,
//            key = { feedback -> feedback.id!! }
//        ) { feedback ->
//            feedback.summary?.let {
//                Text(
//                    text = it
//                )
//            }
//        }
//    }
}

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
                    .width(1.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Column(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
                Text(
                    text = feedback.course_topic,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = feedback.course_date.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun FeedbackList() { // i tried to move fetching dat
    var feedbacks by remember { mutableStateOf<List<Feedback>>(listOf()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            feedbacks = supabase.from("feedbacks")
                .select().decodeList<Feedback>()
        }
    }

}

@Composable
@Preview(showBackground = true)
fun ListScreenPrv(modifier: Modifier = Modifier) {

}
