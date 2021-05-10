package com.kurmakaeva.anastasia.lockquote.paging

//import androidx.paging.PagingSource
//import com.kurmakaeva.anastasia.lockquote.repository.GeniusRepo
//import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel
//import java.lang.Exception
//
//class ListPagingSource(private val geniusRepo: GeniusRepo,
//                       private val query: String): PagingSource<Int, SearchViewModel.SongSummaryViewData>() {
//    private var position = 0
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchViewModel.SongSummaryViewData> {
//        try {
//            position += params.loadSize
//            return LoadResult.Page(
//                data = geniusRepo.searchByTerm(query),
//                prevKey = null,
//                nextKey = position
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//}