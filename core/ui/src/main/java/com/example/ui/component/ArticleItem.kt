package com.example.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.common_kotlin.utils.formatDate
import com.example.common_kotlin.utils.getImageUrl
import com.example.ui.R
import com.example.ui.theme.cornerRadius
import com.example.ui.theme.elevation
import com.example.ui.theme.imageWidth
import com.example.ui.theme.mediumTextSize
import com.example.ui.theme.padding_16
import com.example.ui.theme.padding_8
import com.example.ui.theme.smallTextSize


@Composable
fun ArticleItem(
    article: ArticleModel,
    onArticleClick: (ArticleModel) -> Unit,
    onBookmarkClick: ((ArticleModel) -> Unit)? = null,
) {
    val painter = rememberAsyncImagePainter(
        model = article.getImageUrl(),
        imageLoader = LocalContext.current.imageLoader,
        contentScale = ContentScale.Crop
    )

    val isBookmarked = article.isBookmarked
    val iconTint =
        if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.7f
        )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleClick(article) },
        shape = RoundedCornerShape(cornerRadius),
        elevation = cardElevation(elevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {

            Image(
                painter = painter,
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(imageWidth)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = cornerRadius, bottomStart = cornerRadius
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .padding(padding_16)
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = mediumTextSize
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { onBookmarkClick?.invoke(article) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark
                            else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = iconTint,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(padding_8))

                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.DarkGray,
                        fontSize = smallTextSize
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(padding_8))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = article.source,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = colorResource(R.color.text_medium),
                            fontSize = mediumTextSize
                        )
                    )

                    Text(
                        text = article.publishedDate.formatDate(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.Gray,
                            fontSize = mediumTextSize
                        )
                    )
                }
            }
        }
    }
}
