package com.knthcame.marsrover.ui.movements

import app.cash.turbine.test
import com.knthcame.marsrover.TestCoroutineScopeProvider
import com.knthcame.marsrover.data.calculation.RoverPositionCalculator
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.data.control.repositories.RoverRepository
import com.knthcame.marsrover.ui.movements.MovementsContract.Effect
import com.knthcame.marsrover.ui.movements.MovementsContract.State
import com.knthcame.marsrover.ui.movements.MovementsContract.UiEvent
import com.knthcame.marsrover.ui.navigation.Movements
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovementsViewModelTest {

    private var roverRepository: RoverRepository = mockk()
    private var roverPositionCalculator: RoverPositionCalculator = mockk()

    private var sut = MovementsViewModel(
        route = route,
        roverRepository = roverRepository,
        roverPositionCalculator = roverPositionCalculator,
        coroutineScopeProvider = TestCoroutineScopeProvider(),
    )

    @Test
    fun state_returnsCorrectInstructionsAndInitialPosition_onInit() = runTest {
        sut.state.test {
            assertEquals(expected = initialState, actual = awaitItem())
        }
    }

    @Test
    fun push_emitsNavigateBackEffect_onNavigateBackClickEvent() = runTest {
        sut.effects.test {
            sut.push(UiEvent.NavigateBackClick)

            assertEquals(
                expected = Effect.NavigateBack,
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesMovementsAndPredictedPositions_onAddMovementEvent() = runTest {
        val movement = Movement.MoveForward
        val nextPosition = Position(
            roverPosition = Coordinates(1, 3),
            roverDirection = CardinalDirection.North,
        )
        every {
            roverPositionCalculator.calculateNextPosition(topRightCorner, initialPosition, movement)
        } returns nextPosition

        sut.state.test {
            skipItems(1)

            sut.push(UiEvent.AddMovement(movement))

            assertEquals(
                expected = initialState.copy(
                    instructions = initialState.instructions.copy(
                        movements = movement.code.toString(),
                    ),
                    predictedPositions = listOf(initialPosition, nextPosition),
                ),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun push_updatesMovementsAndPredictedPositions_onRemoveLastMovementEvent() = runTest {
        val movement = Movement.MoveForward
        val nextPosition = Position(
            roverPosition = Coordinates(1, 3),
            roverDirection = CardinalDirection.North,
        )
        every {
            roverPositionCalculator.calculateNextPosition(any(), any(), movement)
        } returns nextPosition
        sut.push(UiEvent.AddMovement(movement))

        sut.state.test {
            assertNotEquals(initialState, awaitItem())

            sut.push(UiEvent.RemoveLastMovement)

            assertEquals(expected = initialState, actual = awaitItem())
        }
    }

    @Test
    fun push_updatesStateToShowOutcomeDialog_onSendMovementsEvent() = runTest {
        val outputPosition = Position(
            roverPosition = Coordinates(1, 3),
            roverDirection = CardinalDirection.North,
        )
        coEvery { roverRepository.send(initialState.instructions) } returns outputPosition

        sut.push(UiEvent.SendMovements)

        sut.state.test {
            val state = awaitItem()
            assert(state.outputReceived)
            assertEquals(
                expected = "${outputPosition.roverPosition.x}" +
                    " ${outputPosition.roverPosition.y}" +
                    " ${outputPosition.roverDirection.code}",
                actual = state.output,
            )
        }
    }

    @Test
    fun push_updatesStateToHideOutcomeDialog_onDismissOutputDialogEvent() = runTest {
        val outputPosition = Position(
            roverPosition = Coordinates(1, 3),
            roverDirection = CardinalDirection.North,
        )
        coEvery { roverRepository.send(any()) } returns outputPosition
        sut.push(UiEvent.SendMovements)

        sut.state.test {
            skipItems(1)
            sut.push(UiEvent.DismissOutputDialog)

            val state = awaitItem()
            assertFalse(state.outputReceived)
            assertEquals(expected = "", actual = state.input)
            assertEquals(expected = "", actual = state.output)
        }
    }

    private companion object {
        private val route = Movements(
            plateauHeight = 5,
            plateauWidth = 5,
            initialPositionX = 1,
            initialPositionY = 2,
            initialDirection = CardinalDirection.North,
        )
        private val initialPosition = Position(
            roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
            roverDirection = route.initialDirection,
        )
        private val topRightCorner = Coordinates(route.plateauWidth, route.plateauHeight)
        private val initialState = State(
            instructions = Instructions(
                topRightCorner = Coordinates(route.plateauWidth, route.plateauHeight),
                roverPosition = Coordinates(route.initialPositionX, route.initialPositionY),
                roverDirection = route.initialDirection,
                movements = "",
            ),
            input = "",
            output = "",
            outputReceived = false,
            predictedPositions = listOf(initialPosition),
        )
    }
}
