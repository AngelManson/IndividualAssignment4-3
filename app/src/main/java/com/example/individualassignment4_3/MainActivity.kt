package com.example.individualassignment4_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.individualassignment4_3.ui.theme.IndividualAssignment43Theme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndividualAssignment43Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainScreen()
//                }

                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var polylineColor by remember { mutableStateOf(Color.Blue) }
    var polylineWidth by remember { mutableStateOf(10f) }
    var polygonColor by remember { mutableStateOf(Color.Magenta) }
    var polygonStrokeWidth by remember { mutableStateOf(5f) }

    var updateText by remember { mutableStateOf("Click on polyline or polygon for info") }

    val hikingTrail = listOf(
        LatLng(37.7749, -122.4194),
        LatLng(37.7799, -122.4294),
        LatLng(37.7849, -122.4194)
    )

    val parkArea = listOf(
        LatLng(37.7750, -122.4180),
        LatLng(37.7760, -122.4200),
        LatLng(37.7775, -122.4190),
        LatLng(37.7765, -122.4170)
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            hikingTrail.first(),
            14f
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Hiking Trail") }) },
        bottomBar = {
//            BottomBar(polygonColor, polylineColor)
            BottomAppBar {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        polylineColor = if (polylineColor == Color.Blue) Color.Red else Color.Blue
                    }) { Text("Change Polyline Color") }

                    Button(onClick = {
                        polygonColor =
                            if (polygonColor == Color.Magenta) Color.Cyan
                            else Color.Magenta
                    }) { Text("Change Polygon Color") }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Polyline(
                    points = hikingTrail,
                    color = polylineColor,
                    width = polylineWidth,
                    clickable = true,
                    onClick = { polyline ->
                        updateText = "Hiking trail Selected"
                    }
                )

                Polygon(
                    points = parkArea,
                    fillColor = polygonColor,
                    strokeColor = polygonColor.copy(alpha = 0.5f),
                    strokeWidth = polygonStrokeWidth,
                    clickable = true,
                    onClick = { polygon ->
                        updateText = "Park area Selected"
                    }
                )
            }

            Text(
                text = updateText,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp),
                color = Color.Black
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
                    .background(Color.White.copy(0.9f))
            ) {
                Text("Polyline Width: ${polylineWidth.toInt()}")
                Slider(
                    value = polylineWidth,
                    onValueChange = { polylineWidth = it },
                    valueRange = 1f..20f
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Polygon Stroke Width: ${polygonStrokeWidth.toInt()}")
                Slider(
                    value = polygonStrokeWidth,
                    onValueChange = { polygonStrokeWidth = it },
                    valueRange = 1f..20f
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Polygon Opacity: ${"%.2f".format(polygonColor.alpha)}")
                Slider(
                    value = polygonColor.alpha,
                    onValueChange = { polygonColor = polygonColor.copy(alpha = it) },
                    valueRange = 0.1f..1f
                )
            }
        }
    }
}

//@Composable
//fun BottomBar(polygonColor: Color, polylineColor: Color) {
//    BottomAppBar {
//        Row(
//            Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(onClick = {
//                polylineColor = if (polylineColor == Color.Blue) Color.Red else Color.Blue
//            }) { Text("Toggle Polyline Color") }
//
//            Button(onClick = {
//                polygonColor =
//                    if (polygonColor.alpha == 0.3f) Color.Yellow.copy(alpha = 0.3f)
//                    else Color.Green.copy(alpha = 0.3f)
//            }) { Text("Toggle Polygon Color") }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    IndividualAssignment43Theme {
//        Greeting("Android")
//    }
//}