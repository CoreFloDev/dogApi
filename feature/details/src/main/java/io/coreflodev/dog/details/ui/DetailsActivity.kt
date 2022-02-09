package io.coreflodev.dog.details.ui

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import io.coreflodev.dog.R
import io.coreflodev.dog.common.arch.Screen
import io.coreflodev.dog.common.nav.Nav
import io.coreflodev.dog.common.theme.DogApiTheme
import io.coreflodev.dog.common.ui.BaseUi
import io.coreflodev.dog.common.ui.LoadImage
import io.coreflodev.dog.details.arch.DetailsInput
import io.coreflodev.dog.details.arch.DetailsNavigation
import io.coreflodev.dog.details.arch.DetailsOutput
import io.coreflodev.dog.details.arch.UiState
import io.coreflodev.dog.details.di.DetailsStateHolder
import io.coreflodev.dog.details.domain.Action
import io.coreflodev.dog.details.domain.Result

class DetailsActivity : ComponentActivity() {

    private lateinit var screen: Screen<DetailsInput, DetailsOutput, DetailsNavigation, Action, Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        screen = ViewModelProvider(
            this,
            DetailsStateHolder.Factory(application, intent.getStringExtra(Nav.DetailsActivityNav.ID) ?: "")
        )
            .get(DetailsStateHolder::class.java)
            .screen

        val (output, input) = screen.attach()

        setContent {
            DogApiTheme {
                val state = output.collectAsState(initial = DetailsOutput())
                BaseUi(id = R.string.detail_title) {
                    Content(output = state.value, input = input)
                }
            }
        }
    }

    override fun onDestroy() {
        screen.detach()
        super.onDestroy()
    }
}

@Composable
fun Content(output: DetailsOutput, input: (DetailsInput) -> Unit) {

    when (output.uiState) {
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
                input(DetailsInput.RetryClicked)
            }) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}
