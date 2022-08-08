package com.example.noteapplication.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "notesTable")
@Parcelize
class Note(
    @ColumnInfo(name = "noteTitle") val noteTitle: String?,
    @ColumnInfo(name = "noteType") val noteType: String?,
    @ColumnInfo(name = "noteDescription") val noteDescription: String?,
    @ColumnInfo(name = "timestamp") val timestamp: String?,
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Int = 0
) : Parcelable

