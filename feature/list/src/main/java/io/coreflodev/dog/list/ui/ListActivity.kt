package io.coreflodev.dog.list.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.R
import io.coreflodev.dog.common.arch.AndroidScreen
import io.coreflodev.dog.common.arch.AndroidView
import io.coreflodev.dog.common.ui.BaseUi
import io.coreflodev.dog.common.ui.LoadImage
import io.coreflodev.dog.list.arch.ListInput
import io.coreflodev.dog.list.arch.ListOutput
import io.coreflodev.dog.list.arch.ScreenState
import io.coreflodev.dog.list.di.ListStateHolder

class ListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screen = ViewModelProvider(
            this,
            ListStateHolder.Factory(application)
        )
            .get(ListStateHolder::class.java)
            .screen

        AndroidScreen(screen, this) { attach ->
            setContent {
                AndroidView(ListOutput(), attach) { state, input ->
                    BaseUi(id = R.string.list_title) {
                        Content(output = state, input = input)
                    }
                }
            }
        }
    }
}

@Composable
fun Content(output: ListOutput, input: (ListInput) -> Unit) {
    when (output.state) {
        is ScreenState.Display -> {
            LazyColumn {
                items(output.state.list) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(bottom = 32.dp)
                            .clickable {
                                input(ListInput.PictureClicked(item.id))
                            }
                    ) {
                        LoadImage(
                            url = item.image,
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (item.name.isNotEmpty()) {
                            Text(
                                text = item.name,
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(vertical = 4.dp)
                                    .background(Color.White.copy(alpha = 0.6f))
                                    .padding(4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        ScreenState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        ScreenState.Retry -> {
            Button(onClick = {
                input(ListInput.RetryClicked)
            }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}
