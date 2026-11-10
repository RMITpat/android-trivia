package com.example.trivia.ui.quiz

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trivia.data.QuestionRepository
import com.example.trivia.network.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import okio.IOException
import javax.inject.Inject

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
            _questionsState.value = try {
                QuestionsState.Success(questionRepository.getQuestions())

            } catch (e: Exception){

                Log.d("opentdb", e.toString())

                QuestionsState.Error
            }
        }
    }

    fun getNextQuestion(questions: List<Question>) {
        val state = _questionsState.value as? QuestionsState.Success ?: return
        if (state.currentIndex + 1 < state.questions.size) {
            _questionsState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
    }



}