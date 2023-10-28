package com.example.xmtechnicalassignment.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.example.xmtechnicalassignment.data.di.dataComponent
import com.example.xmtechnicalassignment.data.repository.QuestionsRepository
import com.example.xmtechnicalassignment.presentation.component.MESSAGE_DELAY
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val questionsRepository: QuestionsRepository = dataComponent().questionsRepository
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(
        MainState()
    )

    fun onStartSurveyClick() {
        intent {
            reduce { state.copy(status = UiStatus.Loading) }
            val result = questionsRepository.loadQuestions()

            if (result.isSuccess) {
                val questions = result.getOrThrow()

                if (questions.isEmpty()) {
                    reduce { state.copy(status = UiStatus.Empty) }
                    delay(MESSAGE_DELAY)
                    reduce { state.copy(status = null) }
                } else {
                    postSideEffect(MainSideEffect.OpenQuestions)
                    reduce { state.copy(status = null) }
                }
            } else {
                reduce { state.copy(status = UiStatus.Error) }
                delay(MESSAGE_DELAY)
                reduce { state.copy(status = null) }
            }
        }
    }
}
