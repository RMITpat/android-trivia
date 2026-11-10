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
    data class Success(val questions: List<Question>) : QuestionsState
}
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository
) : ViewModel() {

    private var _questionsState = MutableStateFlow<QuestionsState>(QuestionsState.Loading)
    val questionState: StateFlow<QuestionsState> = _questionsState.asStateFlow()
//
//    private var _currentQuestion = MutableStateFlow<Question>()
//    val currentQuestion: State<Question> = _currentQuestion.asStateFlow()
//
    init {
        viewModelScope.launch {
            Log.d("opentdb", "Starting question request")
            _questionsState.value = try {
                QuestionsState.Success(questionRepository.getQuestions())
            } catch (e: Exception){
                Log.d("opentdb", e.toString())
                QuestionsState.Error
            }
        }
    }

//    fun getNextQuestion(): Question {
//
//    }



}