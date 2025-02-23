@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composepractice

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composepractice.ui.theme.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = viewModel.snackbarHostState) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(Modifier.fillMaxWidth(0.5f)) {
                        ImageCard()
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(Modifier.fillMaxWidth(0.5f)) {
                        ColorBox(viewModel = viewModel)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    GreetMeTextField(viewModel = viewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageCard(
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painterResource(id = R.drawable.nature),
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Test dev")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = Color.Cyan,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append("Card")
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ColorBox(viewModel: MainViewModel) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = viewModel.color.value, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .pointerInteropFilter {

                if (it.action == MotionEvent.ACTION_DOWN) {
                    viewModel.runChangeColor()
                }

                if (it.action == MotionEvent.ACTION_UP) {
                    viewModel.cancelChangeColor()
                }

                true
            }
    ) {
    }
}

@Composable
fun GreetMeTextField(viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.name.value,
            onValueChange = {
                viewModel.name.value = it
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                viewModel.snackbarHostState.showSnackbar("Hi ${viewModel.name.value}!")
            }
        }) {
            Text(text = "Greet Me!")
        }
    }
}