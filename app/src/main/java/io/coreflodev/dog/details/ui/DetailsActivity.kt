package io.coreflodev.dog.details.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.R
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.arch.ScreenView
import io.coreflodev.dog.common.theme.DogApiTheme
import io.coreflodev.dog.common.ui.LoadImage
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.arch.UiState
import io.coreflodev.dog.details.di.DetailsStateHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class DetailsActivity : ComponentActivity(), ScreenView<DetailsInput, DetailsOutput> {

    companion object {
        fun getStartingIntent(context: Context, id: String) = Intent(context, DetailsActivity::class.java).apply {
            putExtra(ID, id)
        }

        private const val ID = "DOG_ID"
    }

    private lateinit var screen: Screen<DetailsInput, DetailsOutput>

    private val inputChannel = MutableSharedFlow<DetailsInput>(extraBufferCapacity = 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screen = ViewModelProvider(
            this,
            DetailsStateHolder.Factory(application, intent.getStringExtra(ID) ?: "")
        )
            .get(DetailsStateHolder::class.java)
            .screen

        screen.attach(this)
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }

    override fun render(output: DetailsOutput) = when (output) {
        is DetailsOutput.Display -> {
            setContent {
                DogApiTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text(stringResource(id = R.string.detail_title)) }
                                )
                            },
                            content = {
                                Content(output = output, inputChannel)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun inputs(): Flow<DetailsInput> = inputChannel
}

@Composable
fun Content(output: DetailsOutput.Display, input: MutableSharedFlow<DetailsInput>) {

        when(output.uiState) {
            is UiState.Display -> {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = output.uiState.name)
                    LoadImage(
                        url = output.uiState.image,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(text = output.uiState.origin)
                    Text(text = output.uiState.temperament)
                    Text(text = output.uiState.wikiUrl)
                }
            }
            UiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            UiState.Retry -> {
                Button(onClick = {
                    input.tryEmit(DetailsInput.RetryClicked)
                }) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
    }
}
