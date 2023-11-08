package com.example.myapplication.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.api.MovieApi
import com.example.myapplication.db.MovieDatabase
import com.example.myapplication.model.MovieRemoteKeys
import com.example.myapplication.model.Result

@OptIn(ExperimentalPagingApi::class)
class MoviePagingMediator(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : RemoteMediator<Int, Result>() {

    val moviesDao = movieDatabase.movieDao()
    val remoteKeysDao = movieDatabase.movieRemoteKeysDao()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        //fetch movieList From Api
        //save movielist and remotekeys into db
        //logic for refresh,append,prepend
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH ->{
                    val remoteKeys = getRemoteKeyClosestToCurrentItem(state)
                    remoteKeys?.nextPage?.minus(1)?:1
                }


                    LoadType.PREPEND ->{
                        var remoteKeys = getRemoteKeyForFirstItem(state)
                        var prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                       prevPage
                }


                    LoadType.APPEND-> {
                    var remoteKeys = getRemoteKeyForLastItem(state)
                    var nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }
            var response = movieApi.getMovies(currentPage)
            var endofPaginationReached = response.total_pages == currentPage

            var prevPage = if (currentPage ==1)null else currentPage-1
            var nextPage = if(endofPaginationReached)null else currentPage+1
            movieDatabase.withTransaction {
                moviesDao.addMovies(response.results)
               var keys = response.results.map {
                   MovieRemoteKeys(id = it.id,
                       prevPage = prevPage,
                       nextPage = nextPage)

               }
                remoteKeysDao.addRemoteKeys(keys)
            }

            MediatorResult.Success(endofPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)

        }


    }

    suspend fun getRemoteKeyClosestToCurrentItem(state: PagingState<Int, Result>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id = id)

            }

        }
    }

    suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            remoteKeysDao.getRemoteKeys(id = it.id)
        }
    }

    suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeysDao.getRemoteKeys(id = it.id)
        }
    }
}