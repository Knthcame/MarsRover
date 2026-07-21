package com.knthcame.marsrover.ui

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.movements.Movement

const val HomeStartButtonTag = "homeStartButton"
const val HomeTopBarTitleTag = "homeTopBarTitle"

const val SetupContinueButtonTag = "setupContinueButton"
const val SetupPlateauWidthTag = "setupPlateauWidthTextField"
const val SetupPlateauHeightTag = "setupPlateauHeightTextField"
const val SetupInitialXTag = "setupInitialXTextField"
const val SetupInitialYTag = "setupInitialYTextField"
const val SetupInitialDirectionTag = "setupInitialDirectionTextField"
const val SetupTopBarTitleTag = "setupTopBarTitle"

const val SendMovementsButtonTag = "sendMovementsButton"
const val MovementsTextFieldTag = "movementsTextField"
const val MovementsTextFieldTrailingIconTag = "movementsTextFieldTrailingIcon"
const val MovementsTopBarTitleTag = "movementsTopBarTitle"
const val MovementsOutputDialogOutputTextTag = "movementsOutputDialogOutputText"

fun addMovementButtonTag(movement: Movement): String = "add${movement}MovementButton"

fun modalSheetDirectionButtonTag(direction: CardinalDirection): String =
    "modalSheet${direction}DirectionButton"
