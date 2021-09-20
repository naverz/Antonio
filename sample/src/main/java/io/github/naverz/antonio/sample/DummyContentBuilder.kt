package io.github.naverz.antonio.sample

import androidx.lifecycle.LiveData
import io.github.naverz.antonio.R
import io.github.naverz.antonio.sample.antonio.SmallContent
import io.github.naverz.antonio.sample.antonio.RankingContainer
import io.github.naverz.antonio.sample.antonio.ContainerForSmallContents
import kotlin.random.Random

private const val ICON = R.drawable.ic_launcher_background

class ContentBuilder {
    fun makeDummyRankingContainers(onClick: () -> Unit): List<RankingContainer.ContentRanking> {
        return (1..6).map { index ->
            RankingContainer.ContentRanking(
                index,
                ICON,
                "$index name",
                "$index sub content",
                onClick
            )
        }
    }

    fun makeDummyContainersForSmallContents(
        onClick: ((id: String) -> Unit) = {},
        onLongClick: ((id: String) -> Boolean) = { false },
        selectedIds: LiveData<Set<String>>? = null,
        countOfContent: Int = 1000
    ): List<ContainerForSmallContents> {
        val random = Random(System.currentTimeMillis())
        return (1..countOfContent).map { index ->
            makeDummySmallContent(
                index.toString(),
                random.nextInt(100, 1000),
                onClick,
                onLongClick,
                selectedIds
            )
        }.chunked(4).map { ContainerForSmallContents(it) }
    }

    fun makeDummySmallContents(
        count: Int = Random(System.currentTimeMillis()).nextInt(4, 12),
        onClick: ((id: String) -> Unit) = {},
        onLongClick: ((id: String) -> Boolean) = { false },
        selectedIds: LiveData<Set<String>>? = null
    ): List<SmallContent> {
        val random = Random(System.currentTimeMillis())
        val max = random.nextInt(3, count)
        return (1..max).map { index ->
            makeDummySmallContent(
                index.toString(),
                random.nextInt(100, 1000),
                onClick,
                onLongClick,
                selectedIds
            )
        }
    }

    private fun makeDummySmallContent(
        id: String,
        price: Int,
        onClick: ((id: String) -> Unit),
        onLongClick: ((id: String) -> Boolean),
        selectedIds: LiveData<Set<String>>?
    ): SmallContent {
        return SmallContent(
            id,
            ICON,
            price,
            onClick,
            onLongClick,
            selectedIds
        )
    }
}