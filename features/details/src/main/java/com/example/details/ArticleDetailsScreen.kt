package com.example.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.common_kotlin.domain.model.ArticleModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.common_kotlin.utils.getImageUrl
import com.example.core_android.util.shareArticle
import com.example.ui.theme.padding_24
import com.example.ui.R
import com.example.ui.component.CommonTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(
    article: ArticleModel,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CommonTopAppBar(
            title = R.string.details,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    context.shareArticle(article.url)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(
                start = padding_24,
                end = padding_24,
                top = padding_24,
                bottom = padding_24
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.getImageUrl())
                        .crossfade(true).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(padding_24))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )

                Spacer(modifier = Modifier.height(padding_24 / 2))

                Text(
                    text = "By ${article.description}",
                    style = MaterialTheme.typography.labelMedium,
                    color = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Published: ${article.publishedDate}",
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.height(padding_24 / 2))

                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.height(padding_24))


            }
        }
    }
}
