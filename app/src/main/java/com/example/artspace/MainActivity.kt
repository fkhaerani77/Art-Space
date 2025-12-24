package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
fun ArtSpaceApp() {
    var currentStep by remember { mutableIntStateOf(1) }

    // MULAI ANIMASI
    AnimatedContent(
        targetState = currentStep,
        label = "ArtAnimation",
        transitionSpec = {
            if (targetState > initialState) {
                // Animasi MAJU (Next): Masuk dari Kanan, Keluar ke Kiri
                (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> -width } + fadeOut()
                )
            } else {
                // Animasi MUNDUR (Prev): Masuk dari Kiri, Keluar ke Kanan
                (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                    slideOutHorizontally { width -> width } + fadeOut()
                )
            }
        }
    ) { targetStep -> // <--- PENTING: Gunakan variabel 'targetStep' ini di dalam

        // --- PINDAHKAN LOGIKA DATA KE SINI (DI DALAM ANIMATED CONTENT) ---
        val imageResource: Int
        val title: String
        val artist: String
        val year: String
        val genre: String
        val description: String

        // Gunakan 'targetStep' (bukan currentStep) untuk menentukan gambar
        when (targetStep) {
            1 -> {
                imageResource = R.drawable._00px_the_scream
                title = stringResource(R.string.tittle1)
                artist = stringResource(R.string.artist1)
                year = stringResource(R.string.year1)
                genre = stringResource(R.string.genre1)
                description = stringResource(R.string.description1)
            }
            2 -> {
                imageResource = R.drawable.mona_lisa
                title = stringResource(R.string.tittle2)
                artist = stringResource(R.string.artist2)
                year = stringResource(R.string.year2)
                genre = stringResource(R.string.genre2)
                description = stringResource(R.string.description2)
            }
            3 -> {
                imageResource = R.drawable.girl_with_a_pearl_earring
                title = stringResource(R.string.tittle3)
                artist = stringResource(R.string.artist3)
                year = stringResource(R.string.year3)
                genre = stringResource(R.string.genre3)
                description = stringResource(R.string.description3)
            }
            4 -> {
                imageResource = R.drawable.the_arnolfini_portrait
                title = stringResource(R.string.tittle4)
                artist = stringResource(R.string.artist4)
                year = stringResource(R.string.year4)
                genre = stringResource(R.string.genre4)
                description = stringResource(R.string.description4)
            }
            else -> {
                imageResource = R.drawable._00px_the_scream
                title = "Unknown"
                artist = "Unknown"
                year = "Unknown"
                genre = "Unknown"
                description = "Unknown"
            }
        }

        // TAMPILKAN UI (DI DALAM ANIMATED CONTENT)
        ArtStatis(
            imageRes = imageResource,
            tittle1 = title,
            artist1 = artist,
            year1 = year,
            genre1 = genre,
            description1 = description,
            onPrevClick = {
                if (currentStep == 1) {
                    currentStep = 4
                } else {
                    currentStep--
                }
            },
            onNextClick = {
                if (currentStep == 4) {
                    currentStep = 1
                } else {
                    currentStep++
                }
            }
        )
    }
}

@Composable
fun ArtStatis(
    imageRes: Int,
    tittle1: String,
    artist1: String,
    year1: String,
    genre1: String,
    description1: String,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shadowElevation = 10.dp,
            modifier = Modifier.padding(16.dp),
            color = Color.White
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = tittle1,
                contentScale = androidx.compose.ui.layout.ContentScale.Crop, // Agar gambar rapi
                modifier = Modifier // Gunakan Modifier (Baru)
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.White)
                    .padding(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier // Gunakan Modifier (Baru)
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFFE6EAEB))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = tittle1,
                fontSize = 22.sp,
                fontWeight = FontWeight.W200,
            )
            Text(
                text = genre1,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Row {
                Text(
                    text = artist1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = " $year1", // Tambah spasi agar rapi
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = description1,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                textAlign = TextAlign.Justify
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onPrevClick,
                modifier = Modifier.width(150.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495D92))
            ) {
                Text("Previous")
            }
            Button(
                onClick = onNextClick,
                modifier = Modifier.width(150.dp),
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
        ArtStatis(
            imageRes = R.drawable._00px_the_scream,
            tittle1 = "The Scream",
            artist1 = "Edvard Munch",
            year1 = "(1893)",
            genre1 = "Ekspresionisme",
            description1 = "The Scream karya Edvard Munch adalah representasi visual dari kecemasan eksistensial, ketakutan, dan kegelisahan jiwa manusia modern saat menghadapi krisis, terinspirasi dari pengalaman pribadi Munch saat ia merasa \"jeritan alam\" yang tak terbatas melalui langit merah darah yang dramatis setelah letusan Gunung Krakatau. Sosoknya yang terdistorsi menutup telinga, menunjukkan isolasi dan kepanikan, menjadikannya simbol universal dari perasaan manusia yang paling mendalam, bukan sekadar gambaran fisik, tapi emosi yang meluap.",
            onPrevClick = {},
            onNextClick = {}
        )
    }
}