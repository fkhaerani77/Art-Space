package com.example.artspace

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme (
                dynamicColor = false
            ){
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    var showSplashScreen by remember { mutableStateOf(true) }

                    LaunchedEffect(Unit) {
                        delay(2000)
                        showSplashScreen = false
                    }

                    if (showSplashScreen){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ){
                            Column {
                                Text(
                                    "ART SPACE",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "by Ranize",
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                )
                            }
                        }
                    }
                    else {
                        ArtSpaceApp()
                    }

                }
            }
        }
    }
}

data class ArtWork(
    val id: Int,
    val imageRes: Int,
    val titleRes: Int,
    val artistRest: Int,
    val yearRes: Int,
    val genreRes: Int,
    val descriptionRes: Int,
    val artistBioRes: Int
)

@Composable
fun ArtSpaceApp() {
    val artworks = remember {
        listOf(
            ArtWork(
                1,
                R.drawable._00px_the_scream,
                R.string.tittle1,
                R.string.artist1,
                R.string.year1,
                R.string.genre1,
                R.string.description1,
                R.string.artistBio1
            ),
            ArtWork(
                2,
                R.drawable.mona_lisa,
                R.string.tittle2,
                R.string.artist2,
                R.string.year2,
                R.string.genre2,
                R.string.description2,
                R.string.artistBio2
            ),
            ArtWork(
                3,
                R.drawable.girl_with_a_pearl_earring,
                R.string.tittle3,
                R.string.artist3,
                R.string.year3,
                R.string.genre3,
                R.string.description3,
                R.string.artistBio3
            ),
            ArtWork(
                4,
                R.drawable.the_arnolfini_portrait,
                R.string.tittle4,
                R.string.artist4,
                R.string.year4,
                R.string.genre4,
                R.string.description4,
                R.string.artistBio4
            )
        )
    }
    var currentStep by remember { mutableIntStateOf(1) }
    var isSHowingArtistProfile by remember { mutableStateOf(false) }

    var searchQuery by remember { mutableStateOf("") }
    var isSearchError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        if (!isSHowingArtistProfile){
            Surface(
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 15.dp)
            ){
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {query ->
                        searchQuery = query
                        isSearchError = false

                        if (query.isNotEmpty()){
                            val fountArtWork = artworks.find { artWork ->
                                val tittle1 = context.getString(artWork.titleRes)
                                val artist1 = context.getString(artWork.artistRest)
                                tittle1.contains(query, ignoreCase = true) ||
                                        artist1.contains(query, ignoreCase = true)
                            }
                            if (fountArtWork != null){
                                currentStep = fountArtWork.id
                            } else {
                                isSearchError = true
                            }
                        }
                    },label = { Text("Cari Lukisan (Contoh: Mona)") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    isError = isSearchError,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
            }
        }

        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top // <-- TAMBAHKAN BARIS INI
        ) {
            val currentAtWork = artworks[currentStep - 1]
            //Menampilkan Biografi Pelukis
            if (isSHowingArtistProfile) {

                ArtistProfileScreen(
                    artistName1 = stringResource(currentAtWork.artistRest),
                    artistBio1 = stringResource(currentAtWork.artistBioRes),
                    onBackClick = { isSHowingArtistProfile = false }
                )
            } else {
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
                ){ targetStep ->
                    val targetArtwork = artworks[targetStep - 1]

                    ArtStatis(
                        imageRes = targetArtwork.imageRes,
                        tittle1 = stringResource(targetArtwork.titleRes),
                        artist1 = stringResource(targetArtwork.artistRest),
                        year1 = stringResource(targetArtwork.yearRes),
                        genre1 = stringResource(targetArtwork.genreRes),
                        description1 = stringResource(targetArtwork.descriptionRes),
                        currentStep = targetStep,
                        totalSteps = artworks.size,
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
                        },
                        onArtistCLick = {isSHowingArtistProfile = true}
                    )
                }
            }
        }
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
    currentStep: Int,
    totalSteps: Int,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onArtistCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val customFont = FontFamily(Font(R.font.cinzel_black))
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context, null) }

    if (isLandscape){
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .safeDrawingPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 16.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    ZoomableImage(
                        imageRes = imageRes,
                        contentDescription = tittle1,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                ) {


                    Text(text = tittle1,
                        fontSize = 22.sp,
                        fontFamily = customFont,
                        fontWeight = FontWeight.W300)
                    Text(text = genre1, fontSize = 14.sp, fontStyle = FontStyle.Italic, color = Color.Gray)

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = artist1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onArtistCLick() }
                        )

                        Text(text = " $year1",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Thin
                        )

                        IconButton(onClick = {
                            tts.speak(description1, TextToSpeech.QUEUE_FLUSH, null, null)
                        }) {
                            Icon(
                                Icons.Default.PlayArrow, contentDescription = "Baca Deskripsi"
                            )
                        }

                        IconButton(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND

                                    putExtra(Intent.EXTRA_TEXT, "Lihat Lukisan keren ini: \"$tittle1\" karya $artist1. \n\n$description1")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Bagikan lukisan via...")
                                context.startActivity(shareIntent)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share Lukisan",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = description1,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Justify
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                PageIndicator(totalSteps = totalSteps, currentStep =currentStep)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onPrevClick,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) { Text("Prev") }
                    Button(
                        onClick = onNextClick,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) { Text("Next") }
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(16.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) // Tinggi area gambar
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(24.dp)
                ) {
                    // --- GANTI IMAGE LAMA DENGAN ZOOMABLE IMAGE ---
                    ZoomableImage(
                        imageRes = imageRes,
                        contentDescription = tittle1,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            PageIndicator(totalSteps = totalSteps, currentStep = currentStep)


            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = tittle1,
                    fontFamily = customFont,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W200
                )
                Text(
                    text = genre1,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = artist1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable{onArtistCLick()}
                    )

                    Text(
                        text = " $year1",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Thin
                    )

                    IconButton(onClick = {
                        tts.speak(description1, TextToSpeech.QUEUE_FLUSH, null, null)
                    }) {
                        Icon(
                            Icons.Default.PlayArrow, contentDescription = "Baca Deskripsi",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND

                                putExtra(Intent.EXTRA_TEXT, "Lihat Lukisan keren ini: \"$tittle1\" karya $artist1. \n\n$description1")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Bagikan lukisan via...")
                            context.startActivity(shareIntent)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Lukisan",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description1,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Previous")
                }
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.width(150.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
fun ArtistProfileScreen (
    artistName1: String,
    artistBio1: String,
    onBackClick: () -> Unit
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
       Surface (
           shape = RectangleShape,
           color = Color(0xFF495D92),
           modifier = Modifier
               .height(120.dp)
               .width(120.dp)
       )
        {
            Image(
                painter = painterResource(R.drawable.edvard_munch),
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(20.dp)
        ){
            Text(
                text = artistName1,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFF495D92),
                modifier = Modifier
                    .padding(bottom = 5.dp)
            )
            Text(
                text = "Biografi Singkat",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = artistBio1,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Kembali ke Lukisan")
        }
    }

}

@Composable
fun ZoomableImage(
    imageRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    // State untuk skala zoom dan posisi geser
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .clip(RectangleShape) // Agar gambar tidak keluar dari kotaknya saat di-zoom
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // 1. Hitung Zoom
                    scale *= zoom
                    // Batasi zoom minimal 1x (normal) dan maksimal 4x
                    scale = scale.coerceIn(1f, 4f)

                    // 2. Hitung Geser (Pan)
                    // Geser hanya aktif jika gambar sedang diperbesar (scale > 1)
                    if (scale > 1f) {
                        val newOffset = offset + pan
                        // Kita bisa tambahkan logika batas geser disini,
                        // tapi untuk simpelnya kita biarkan bebas dulu selama di-zoom
                        offset = newOffset
                    } else {
                        // Jika kembali ke ukuran normal, reset posisi ke tengah
                        offset = Offset.Zero
                    }
                }
            }
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = contentDescription,
            contentScale = androidx.compose.ui.layout.ContentScale.Fit, // Gunakan Fit agar proporsional
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        )
    }
}

@Composable
fun PageIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier
){
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ){
        repeat(totalSteps) {index ->
            val isActive = (index + 1) == currentStep

            val color = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f)
            val size = if (isActive) 12.dp else 8.dp

            Box (
                modifier = Modifier
                    .padding(4.dp)
                    .size(size)
                    .clip(shape = CircleShape)
                    .background(color)
            ){}
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
            currentStep = 1,
            totalSteps = 4,
            onPrevClick = {},
            onNextClick = {},
            onArtistCLick = {}
        )
    }
}

