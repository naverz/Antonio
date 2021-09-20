package io.github.naverz.antonio.sample.fragmenthost.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.naverz.antonio.sample.ContentBuilder
import io.github.naverz.antonio.sample.antonio.ContainerForSmallContents
import kotlinx.coroutines.delay

class SamplePagingSource : PagingSource<Int, ContainerForSmallContents>() {
    private val contentBuilder = ContentBuilder()
    private var count = 0
    override fun getRefreshKey(state: PagingState<Int, ContainerForSmallContents>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContainerForSmallContents> {
        delay(500)
        count++
        return LoadResult.Page(
            data = contentBuilder.makeDummyContainersForSmallContents(countOfContent = 36),
            prevKey = null,
            nextKey = count
        )
    }
}