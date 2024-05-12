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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cfs.R
import java.util.Date

@Composable
fun ListScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(feedbacks) {
            FeedbackItem(it)
        }
    }
}

@Composable
fun FeedbackItem(
    feedback: Feedback,
    modifier: Modifier = Modifier
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
                    text = feedback.courseName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )


            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()  //fill the max height
                    .width(1.dp)
            )

            Column(modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
                Text(
                    text = feedback.topic,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = feedback.courseDate.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

data class Feedback(
    val courseName: String,
    val topic: String,
    val courseDate: Date,
    val feedback: String = ""
)

val feedbacks = listOf(
    Feedback("SENG 101", "Visiting your mom ", Date(2024, 5, 10), "Great course, learned a lot!"),
    Feedback(
        "COMP 202",
        "Your dad returns ",
        Date(2024, 4, 15),
        "The instructor was very knowledgeable."
    ),
    Feedback(
        "MATH 301",
        "Calculating gravitational pull of your mom ",
        Date(2024, 3, 20),
        "Found the content challenging but rewarding."
    )
)

@Composable
@Preview(showBackground = true)
fun ListScreenPrv(modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(feedbacks) {
            FeedbackItem(it)
        }
    }
}