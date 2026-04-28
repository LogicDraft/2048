package com.antigravity.twentyfortyeight.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gridSize: Int,
    val score: Int,
    val maxTile: Int,
    val date: String = SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Date())
)
