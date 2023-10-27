package com.example.xmtechnicalassignment.presentation.ui.questions

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.xmtechnicalassignment.data.di.dataComponent
import com.example.xmtechnicalassignment.data.remote.entity.SubmitQuestion
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import com.example.xmtechnicalassignment.presentation.component.MESSAGE_DELAY
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class QuestionsViewModel(
    private val questionsRepository: QuestionsRepository = dataComponent().questionsRepository
) : ContainerHost<QuestionsScreenState, QuestionsSideEffect>, ViewModel() {

    override val container = container<QuestionsScreenState, QuestionsSideEffect>(
        createInitState()
    )

    init {
        if (questionsRepository.questions.isEmpty()) {
            intent {
                postSideEffect(QuestionsSideEffect.CloseQuestions)
            }
        }
    }

    private fun createInitState(): QuestionsScreenState {
        val items = questionsRepository.questions.map { QuestionState(question = it) }
        return QuestionsScreenState(questions = items.toMutableStateList())
    }

    fun onSubmitQuestion(submitQuestion: SubmitQuestion) {
        intent {
            val index = state.questions.indexOfFirst { it.question.id == submitQuestion.id }
            if (index < 0) {
                //TODO show error
            }
            reduceQuestions(index, UiStatus.Loading)
            val result = questionsRepository.submitQuestion(submitQuestion)

            if (result.isSuccess) {
                reduce {
                    val questionState = state.questions[index]
                    state.questions[index] = questionState.copy(
                        status = UiStatus.Success,
                        answer = submitQuestion.answer,
                        submittedAnswer = submitQuestion.answer,
                    )
                    state
                }
                delay(MESSAGE_DELAY)
                reduceQuestions(index, null)
            } else {
                reduce {
                    val questionState = state.questions[index]
                    state.questions[index] = questionState.copy(
                        status = UiStatus.Error,
                        answer = submitQuestion.answer,
                    )
                    state
                }
                delay(MESSAGE_DELAY)
                reduceQuestions(index, null)
            }
        }
    }

    private suspend fun SimpleSyntax<QuestionsScreenState, QuestionsSideEffect>.reduceQuestions(
        index: Int,
        status: UiStatus?
    ) {
        reduce {
            val questionState = state.questions[index]
            state.questions[index] = questionState.copy(status = status)
            state
        }
    }

}