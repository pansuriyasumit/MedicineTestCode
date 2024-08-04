package com.fifteen11.checkappversion.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {
    /**
     * Insert User Details
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLoginHistory(userLoginHistoryEntity: UserLoginHistoryEntity): Long

    /**
     * Get User Login History
     */
    @Query("SELECT * FROM tbl_user_login_history")
    fun getUserLoginHistory(): Flow<List<UserLoginHistoryEntity>>

}