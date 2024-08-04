package com.fifteen11.checkappversion.data.local.repository

import com.fifteen11.checkappversion.data.local.MedicineDao
import com.fifteen11.checkappversion.data.local.UserLoginHistoryEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MedicineLocalRepositoryImpl @Inject constructor(
    private val medicineDao: MedicineDao
) : MedicineLocalRepository {

    override suspend fun insertUserLoginHistory(userEntity: UserLoginHistoryEntity) {
        withContext(IO) {
            medicineDao.insertUserLoginHistory(userLoginHistoryEntity = userEntity)
        }
    }

    override suspend fun getUserLoginHistory(): Flow<List<UserLoginHistoryEntity>> {
        return withContext(IO) {
            medicineDao.getUserLoginHistory()
        }
    }

}