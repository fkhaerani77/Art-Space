package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                    ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp(){
    var currentStep by remember { mutableIntStateOf(1) }
    val imageResource: Int
    val title: String
    val artist: String
    val year: String

    when (currentStep){
        1 -> {
            imageResource = R.drawable._00px_the_scream
            title = stringResource(R.string.tittle1)
            artist = stringResource(R.string.artist1)
            year = stringResource(R.string.year1)
        }
        2 -> {
            imageResource = R.drawable.mona_lisa
            title = stringResource(R.string.tittle2)
            artist = stringResource(R.string.artist2)
            year = stringResource(R.string.year2)
        }
        3 -> {
            imageResource = R.drawable.girl_with_a_pearl_earring
            title = stringResource(R.string.tittle3)
            artist = stringResource(R.string.artist3)
            year = stringResource(R.string.year3)
        }
        4 -> {
            imageResource = R.drawable.the_arnolfini_portrait
            title = stringResource(R.string.tittle4)
            artist = stringResource(R.string.artist4)
            year = stringResource(R.string.year4)
        }
        else -> { // Default fallback (jika error)
            imageResource = R.drawable._00px_the_scream
            title = "Unknown"
            artist = "Unknown"
            year = "Unknown"
        }
    }

    ArtStatis(
        imageRes = imageResource,
        tittle1 = title,
        artist1 = artist,
        year1 = year,

        onPrevClick = {
            if (currentStep ==1){
                currentStep = 4
            }
            else {
                currentStep--
            }
        },

        onNextClick = {
            if (currentStep == 4){
                currentStep = 1
            }
            else {
                currentStep++
            }
        }
    )
}

@Composable
fun ArtStatis(
    imageRes: Int,
    tittle1: String,
    artist1: String,
    year1: String,
    onPrevClick: () -> Unit, // Menerima fungsi tombol
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shadowElevation = 10.dp,
            modifier = Modifier
                .padding(16.dp),
            color = Color.White
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = "Lukisan1",
                modifier = modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.White)
                    .padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color(0xFFE6EAEB))
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = tittle1,
                fontSize = 20.sp,
                fontWeight = FontWeight.W200,
            )
            Row {
                Text(
                    text = artist1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = year1,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin
                )
            }


        }
        Spacer(modifier = Modifier.height(24.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Button(
                onClick = onPrevClick,
                modifier = Modifier
                    .width(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92))
            ) {
                Text("Previous")
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .width(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92))
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtStatis(imageRes = R.drawable._00px_the_scream,
            tittle1 = "The Scream",
            artist1 = "Edvard Munch",
            year1 = "(1893)",
            onPrevClick = {},
            onNextClick = {})
    }
}