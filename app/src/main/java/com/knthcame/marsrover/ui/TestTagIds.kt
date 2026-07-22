package com.knthcame.marsrover.ui

import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.ui.movements.Movement

const val HOME_START_BUTTON_TAG = "homeStartButton"
const val HOME_TOP_BAR_TITLE_TAG = "homeTopBarTitle"

const val SETUP_CONTINUE_BUTTON_TAG = "setupContinueButton"
const val SETUP_PLATEAU_WIDTH_TAG = "setupPlateauWidthTextField"
const val SETUP_PLATEAU_HEIGHT_TAG = "setupPlateauHeightTextField"
const val SETUP_INITIAL_X_TAG = "setupInitialXTextField"
const val SETUP_INITIAL_Y_TAG = "setupInitialYTextField"
const val SETUP_INITIAL_DIRECTION_TAG = "setupInitialDirectionTextField"
const val SETUP_TOP_BAR_TITLE_TAG = "setupTopBarTitle"

const val SEND_MOVEMENTS_BUTTON_TAG = "sendMovementsButton"
const val MOVEMENTS_TEXT_FIELD_TAG = "movementsTextField"
const val MOVEMENTS_TEXT_FIELD_TRAILING_ICON_TAG = "movementsTextFieldTrailingIcon"
const val MOVEMENTS_TOP_BAR_TITLE_TAG = "movementsTopBarTitle"
const val MOVEMENTS_OUTPUT_DIALOG_OUTPUT_TEXT_TAG = "movementsOutputDialogOutputText"

fun addMovementButtonTag(movement: Movement): String = "add${movement}MovementButton"

fun modalSheetDirectionButtonTag(direction: CardinalDirection): String =
    "modalSheet${direction}DirectionButton"
