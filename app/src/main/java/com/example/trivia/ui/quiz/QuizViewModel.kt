package com.example.trivia.ui.quiz

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trivia.data.QuestionRepository
import com.example.trivia.network.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import okio.IOException
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

sealed interface QuestionsState {
    object Error : QuestionsState
    object Loading : QuestionsState
    data class Success(
        val questions: List<Question>,
        val currentIndex: Int = 0
        ) : QuestionsState
}
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {


    private var _questionsState = MutableStateFlow<QuestionsState>(QuestionsState.Loading)
    val questionState: StateFlow<QuestionsState> = _questionsState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _questionsState.value = QuestionsState.Success(questionRepository.getQuestions())
                startQuestionTimer()
            } catch (e: Exception){

                Log.d("opentdb", e.toString())

                _questionsState.value = QuestionsState.Error
            }

        }
    }

    private var _selectedAnswer = MutableStateFlow("")
    val selectedAnswer: StateFlow<String> = _selectedAnswer.asStateFlow()

    private var _questionTimer = MutableStateFlow(0f)
    val questionTimer: StateFlow<Float> = _questionTimer

    private var _questionEnabled = MutableStateFlow(true)
    val questionEnabled: StateFlow<Boolean> = _questionEnabled


    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun nextQuestion() {
        val state = _questionsState.value as? QuestionsState.Success ?: return
        if (state.currentIndex + 1 < state.questions.size) {
            _questionsState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
        startQuestionTimer()
    }

    fun startQuestionTimer() {
        viewModelScope.launch {
            _questionEnabled.value = true
            _questionTimer.value = 0f


            for (i in 1..200) {
                delay(50.milliseconds)
                _questionTimer.value = i / 200f
            }

            _questionEnabled.value = false

        }


    }


}