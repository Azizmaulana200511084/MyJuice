package com.aziz.myjuice.dataroom.repository

import com.aziz.myjuice.dataroom.local.MyJuiceDao
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val myjuiceDao: MyJuiceDao) {
    fun getAllMyJuice() = myjuiceDao.getAllMyJuice()
    fun getAllFavoriteMyJuice() = myjuiceDao.getAllFavoriteMyJuice()
    fun getMyJuice(id: Int) = myjuiceDao.getMyJuice(id)
    fun searchMyJuice(query: String) = myjuiceDao.searchMyJuice(query)
    suspend fun insertAllMyJuice(myjuice: List<MyJuiceEntity>) = myjuiceDao.insertAllMyJuice(myjuice)
    suspend fun updateFavoriteMyJuice(id: Int, isFavorite: Boolean) = myjuiceDao.updateFavoriteMyJuice(id, isFavorite)
}
