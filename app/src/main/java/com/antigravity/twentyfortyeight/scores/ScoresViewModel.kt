package com.antigravity.twentyfortyeight.scores

import androidx.lifecycle.ViewModel
import com.antigravity.twentyfortyeight.data.GameRepository
import com.antigravity.twentyfortyeight.data.ScoreEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val repo: GameRepository
) : ViewModel() {
    fun getTop10(gridSize: Int): Flow<List<ScoreEntity>> = repo.getTop10Scores(gridSize)
}
