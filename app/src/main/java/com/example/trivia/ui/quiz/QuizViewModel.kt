package com.example.trivia.ui.quiz

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trivia.data.QuestionRepository
import com.example.trivia.network.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.connection.Exchange
import okio.IOException
import retrofit2.HttpException
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
        fetchQuestions()
    }

    /*
       Fetch questions from OpenTDB
     */
    fun fetchQuestions() {
        _questionsState.value = QuestionsState.Loading

        viewModelScope.launch {
            try {
                _questionsState.value = QuestionsState.Success(questionRepository.getQuestions())
                startQuestionTimer()

            } catch (e: HttpException) {
                if (e.code() == 429) {
                    delay(2.seconds)
                    fetchQuestions()
                } else {
                    _questionsState.value = QuestionsState.Error
                }
            }
            catch (e: Exception){

                Log.d("opentdb", e.toString())

                _questionsState.value = QuestionsState.Error
            }

        }
    }

    // The user selected answer
    private var _selectedAnswer = MutableStateFlow("")
    val selectedAnswer: StateFlow<String> = _selectedAnswer.asStateFlow()

    // The current timer value for a question
    private var _questionTimer = MutableStateFlow(0f)
    val questionTimer: StateFlow<Float> = _questionTimer

    // Whether the answer buttons are enabled
    private var _questionEnabled = MutableStateFlow(true)
    val questionEnabled: StateFlow<Boolean> = _questionEnabled

    // The current ongoing timer, stored for early cancellation
    private var currentTimer: Job? = null
    
    /**
    Select an answer
     */
    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    /**
     * Change the current question to the next
     */
    fun nextQuestion() {

        val state = _questionsState.value as? QuestionsState.Success ?: return
        if (state.currentIndex + 1 < state.questions.size) {
            _questionsState.value = state.copy(currentIndex = state.currentIndex + 1)
        }
        startQuestionTimer()
    }

    /**
     * Submit an answer, disabling answer input, cancelling the timer if still ongoing and finishing it
     *
     */

    fun submitAnswer() {
        disableQuestion()
        currentTimer?.cancel()
        _questionTimer.value = 1f

    }

    /**
     * Helper function for disabling answer input
     */
    private fun disableQuestion() {
        _questionEnabled.value = false
    }

    /**
     * Helper function for enabling answer input
     */
    private fun enableQuestion() {
        _questionEnabled.value = true
    }

    /**
     * Start the question timer for a new question
     */
    fun startQuestionTimer() {
        currentTimer = viewModelScope.launch {
            enableQuestion()
            _questionTimer.value = 0f


            for (i in 1..200) {
                delay(50.milliseconds)
                _questionTimer.value = i / 200f
            }

            disableQuestion()
        }


    }


}