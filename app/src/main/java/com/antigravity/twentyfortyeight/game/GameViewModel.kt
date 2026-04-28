package com.antigravity.twentyfortyeight.game

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antigravity.twentyfortyeight.data.GameRepository
import com.antigravity.twentyfortyeight.engine.Direction
import com.antigravity.twentyfortyeight.engine.GameBoard
import com.antigravity.twentyfortyeight.engine.ScoreCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameState(
    val board: GameBoard = GameBoard.create(4),
    val bestScore: Int = 0,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
    val continueAfterWin: Boolean = false,
    val hasUndo: Boolean = false,
    val moveCount: Int = 0
)

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repo: GameRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private val undoHistory = mutableListOf<GameBoard>()
    private var hapticsEnabled = true
    private var currentGridSize = 4

    init {
        viewModelScope.launch {
            hapticsEnabled = repo.hapticsEnabled.first()
            currentGridSize = repo.defaultGridSize.first()
            val saved = repo.loadGame()
            val best = repo.getBestScore(currentGridSize)
            if (saved != null) {
                _state.value = GameState(
                    board = saved,
                    bestScore = best,
                    hasUndo = false
                )
            } else {
                _state.value = GameState(
                    board = GameBoard.create(currentGridSize),
                    bestScore = best
                )
            }
        }
    }

    fun startNewGame(gridSize: Int = currentGridSize) {
        currentGridSize = gridSize
        undoHistory.clear()
        viewModelScope.launch {
            val best = repo.getBestScore(gridSize)
            _state.value = GameState(
                board = GameBoard.create(gridSize),
                bestScore = best
            )
            repo.clearSavedGame()
        }
    }

    fun swipe(direction: Direction) {
        val current = _state.value
        if (current.isGameOver || (current.isWon && !current.continueAfterWin)) return

        val (newBoard, moved) = when (direction) {
            Direction.LEFT -> current.board.moveLeft()
            Direction.RIGHT -> current.board.moveRight()
            Direction.UP -> current.board.moveUp()
            Direction.DOWN -> current.board.moveDown()
        }

        if (!moved) return

        pushUndoState(current.board)

        val bestScore = maxOf(current.bestScore, newBoard.score)
        val isWon = newBoard.isWon() && !current.continueAfterWin
        val isGameOver = newBoard.isGameOver()

        _state.value = current.copy(
            board = newBoard,
            bestScore = bestScore,
            isGameOver = isGameOver,
            isWon = isWon,
            hasUndo = undoHistory.isNotEmpty(),
            moveCount = current.moveCount + 1
        )

        if (hapticsEnabled) vibrate()

        // Auto-save and score on game over
        if (isGameOver || isWon) {
            viewModelScope.launch {
                repo.saveScore(
                    gridSize = newBoard.gridSize,
                    score = newBoard.score,
                    maxTile = ScoreCalculator.maxTile(newBoard)
                )
            }
        } else {
            viewModelScope.launch { repo.saveGame(newBoard) }
        }
    }

    fun undo() {
        val prev = undoHistory.popLastOrNull() ?: return
        _state.value = _state.value.copy(
            board = prev,
            isGameOver = false,
            isWon = false,
            hasUndo = undoHistory.isNotEmpty(),
            moveCount = (_state.value.moveCount - 1).coerceAtLeast(0)
        )
    }

    fun continueAfterWin() {
        _state.value = _state.value.copy(isWon = false, continueAfterWin = true)
    }

    fun saveCurrentGame() {
        viewModelScope.launch { repo.saveGame(_state.value.board) }
    }

    private fun pushUndoState(board: GameBoard) {
        undoHistory += board
        if (undoHistory.size > MAX_UNDO_HISTORY) {
            undoHistory.removeAt(0)
        }
    }

    private fun MutableList<GameBoard>.popLastOrNull(): GameBoard? =
        if (isEmpty()) null else removeAt(lastIndex)

    private fun vibrate() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                val vm = appContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vm.defaultVibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val v = appContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                v.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } catch (_: Exception) {}
    }

    companion object {
        private const val MAX_UNDO_HISTORY = 5
    }
}
