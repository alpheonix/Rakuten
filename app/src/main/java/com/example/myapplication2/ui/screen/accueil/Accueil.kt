package com.example.myapplication2.ui.screen.accueil

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication2.R
import com.example.myapplication2.models.ProductDetail
import com.example.myapplication2.ui.theme.MyApplication2Theme
import com.example.rakutentest.Model.SearchProduct
import com.example.rakutentest.Model.Search
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccueilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: AccueilViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            MyApplication2Theme {
                MyScreen(viewModel)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(viewModel: AccueilViewModel) {

    val data by viewModel.data.collectAsState()
    var value by remember { mutableStateOf("") }
    val isPressed by viewModel.isPressed.collectAsState()
    val detail by viewModel.detail.collectAsState()

    Column {
        TopAppBar(
            title = { Text("Rakuten") },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xffbf0000))
        )
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(5.dp)
        ) {
            TextField(
                shape = RectangleShape,
                value = value,
                onValueChange = { value = it },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
            )
            SquareButtonWithIcon({ viewModel.fetchData(query = value) })
        }
        if (isPressed) {
            DetailProduct(detail)
        } else {
            SearchList(data, viewModel)
        }


    }
}

@Composable
fun DetailProduct(detail: ProductDetail) {

    Column {
        Image(
            painter = rememberAsyncImagePainter(detail.images[0].imagesUrls.entry[0].url),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
        Row(modifier = Modifier.padding(top = 50.dp)) {
            Box(Modifier.fillMaxWidth(0.7f)) {
                Text(
                    detail.headline,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 5.dp)

                )
            }
            Box {

                Text(
                    detail.salePrice.toString() + " €",
                    color = Red,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(Alignment.TopEnd)
                )
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            stringResource(R.string.Vendeur)+": "+detail.seller.login,
            modifier = Modifier.padding(top = 5.dp, bottom = 20.dp)
        )
        Text(detail.description, fontStyle = FontStyle.Italic)
    }


}

@Composable
fun SearchList(data: Search, viewModel: AccueilViewModel) {
    if (data.title == "title") {
        Text("Chargement...")
    } else {
        LazyColumn {
            items(data.products) { product ->

                ProductItem(product, onItemClick = { _ ->
                    viewModel.isPressed()
                })

            }
        }
    }
}

@Composable
fun ProductItem(searchProduct: SearchProduct, onItemClick: (SearchProduct) -> Unit) {
    Row(modifier = Modifier
        .clickable { onItemClick(searchProduct) }
        .padding(5.dp)) {
        Image(
            painter = rememberAsyncImagePainter(searchProduct.imagesUrls[0]),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Column {
            Text(text = searchProduct.headline, fontWeight = FontWeight.Bold)
            Text(
                searchProduct.newBestPrice.toString() + " €",
                color = Red,
                modifier = Modifier.padding(5.dp)
            )
            StarRatingBar(
                maxStars = 5,
                rating = searchProduct.reviewsAverageNote,

                )


        }

    }

}

@Composable
fun SquareButtonWithIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RectangleShape,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 5.dp), // Forme carrée avec bords droits
        colors = ButtonDefaults.buttonColors(Color.Gray),
        elevation = ButtonDefaults.elevatedButtonElevation(4.dp),
    ) {
        Icon(

            imageVector = Icons.Default.Search, // Icône de loupe
            contentDescription = "Search", modifier = Modifier.fillMaxSize() // Taille de l'icône
        )
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication2Theme {}
}