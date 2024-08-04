package com.fifteen11.checkappversion.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = tableNameLoginHistory)
data class UserLoginHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var userName: String,

    @ColumnInfo(name = "created_at")
    var createdAt: Date = Date(System.currentTimeMillis())

) : Parcelable {
    constructor(userName: String) : this(0, userName)
}

const val tableNameLoginHistory = "tbl_user_login_history"
