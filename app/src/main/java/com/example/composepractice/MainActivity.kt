package com.example.composepractice

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.composepractice.ui.theme.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp)
            ) {
                ImageCard()
                Spacer(modifier = Modifier.height(16.dp))
                ColorBox(
                    lifecycleScope = lifecycleScope,
                    viewModel = viewModel,
                    color = viewModel.color.value
                ) {
                    viewModel.color.value = it
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
                            append("Test ")
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
fun ColorBox(
    lifecycleScope: LifecycleCoroutineScope,
    viewModel: MainViewModel,
    color: Color,
    updateColor: (Color) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = color, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .pointerInteropFilter {

                if (it.action == MotionEvent.ACTION_DOWN) {
                }

                if (it.action == MotionEvent.ACTION_UP) {
                    Log.e("TAG", "UPPPPPP", )
                    viewModel.cancelChangeColor()
                }


                Log.e("TAG", "isBreak: ${viewModel.isBreak.value}", )
                true
            }
    ) {
    }
}