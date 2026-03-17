package com.knthcame.marsrover.ui.setup

import app.cash.turbine.test
import com.knthcame.marsrover.TestCoroutineScopeProvider
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.navigation.Movements
import com.knthcame.marsrover.ui.setup.SetupContract.Effect.NavigateToMovements
import com.knthcame.marsrover.ui.setup.SetupContract.UiEvent
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SetupViewModelTest {
    private val defaultUiState get() = SetupContract.State.default()
    private val viewModel = SetupViewModel(
        coroutineScopeProvider = TestCoroutineScopeProvider(),
    )

    @Test
    fun uiState_returnsDefaultState_onInit() {
        val actual = viewModel.state.value

        assert(actual == defaultUiState)
    }

    @Test
    fun push_updatesPlateauHeight_whenValueIsEmpty() = runTest {
        val newValue = ""

        viewModel.push(UiEvent.PlateauHeightChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(plateauHeight = null),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesPlateauHeight_whenValueIsInteger() = runTest {
        val newValue = 12

        viewModel.push(UiEvent.PlateauHeightChanged(newValue.toString()))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(plateauHeight = newValue),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_doesNotUpdatePlateauHeight_whenValueIsNotAnInteger() = runTest {
        val newValue = "5f"

        viewModel.push(UiEvent.PlateauHeightChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState,
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesPlateauWidth_whenValueIsEmpty() = runTest {
        val newValue = ""

        viewModel.push(UiEvent.PlateauWidthChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(plateauWidth = null),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesPlateauWidth_whenValueIsInteger() = runTest {
        val newValue = 12

        viewModel.push(UiEvent.PlateauWidthChanged(newValue.toString()))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(plateauWidth = newValue),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_doesNotUpdatePlateauWidth_whenValueIsNotAnInteger() = runTest {
        val newValue = "5f"

        viewModel.push(UiEvent.PlateauWidthChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState,
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesInitialX_whenValueIsEmpty() = runTest {
        val newValue = ""

        viewModel.push(UiEvent.InitialXChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(initialX = null),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesInitialX_whenValueIsInteger() = runTest {
        val newValue = 4

        viewModel.push(UiEvent.InitialXChanged(newValue.toString()))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(initialX = newValue),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_doesNotUpdateInitialX_whenValueIsNotAnInteger() = runTest {
        val newValue = "2a"

        viewModel.push(UiEvent.InitialXChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState,
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesInitialY_whenValueIsEmpty() = runTest {
        val newValue = ""

        viewModel.push(UiEvent.InitialYChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(initialY = null),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesInitialY_whenValueIsInteger() = runTest {
        val newValue = 1

        viewModel.push(UiEvent.InitialYChanged(newValue.toString()))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(initialY = newValue),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_doesNotUpdateInitialY_whenValueIsNotAnInteger() = runTest {
        val newValue = "3k"

        viewModel.push(UiEvent.InitialYChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState,
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesInitialDirection_onInitialDirectionChangedEvent() = runTest {
        val newValue = CardinalDirection.South

        viewModel.push(UiEvent.InitialDirectionChanged(newValue))

        viewModel.state.test {
            assertEquals(
                expected = defaultUiState.copy(initialDirection = newValue),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_emitsNavigateToMovementsEffect_onContinueClicked() = runTest {
        viewModel.effects.test {
            viewModel.push(UiEvent.OnContinueClicked)

            assertEquals(
                expected = NavigateToMovements(
                    movements = Movements(
                        plateauHeight = defaultUiState.plateauHeight!!,
                        plateauWidth = defaultUiState.plateauWidth!!,
                        initialPositionX = defaultUiState.initialX!!,
                        initialPositionY = defaultUiState.initialY!!,
                        initialDirection = defaultUiState.initialDirection,
                    ),
                ),
                actual = awaitItem(),
            )
        }
    }
}
