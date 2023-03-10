package com.subha.optymize.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.subha.optymize.core.util.Constants.INITIAL_LOAD_SIZE
import com.subha.optymize.core.util.Constants.NETWORK_PAGE_SIZE
import com.subha.optymize.data.model.Product
import com.subha.optymize.data.remote.api.ShoeaApi


class ShoeaPagingSource(val api: ShoeaApi) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Product> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset =
            if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE
        return try {
            val response = api.getproductlist(skip = offset, limit = params.loadSize)
            val nextKey = if (response.products.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response.products,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}