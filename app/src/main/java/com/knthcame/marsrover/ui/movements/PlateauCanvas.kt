package com.knthcame.marsrover.ui.movements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R
import com.knthcame.marsrover.data.control.model.Coordinates

@Composable
fun PlateauCanvas(
    uiState: MovementsUiState,
    primaryColor: Color,
    modifier: Modifier = Modifier,
) {
    val lineColor = MaterialTheme.colorScheme.onBackground

    Canvas(
        modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
    ) {
        drawPlateauGrid(uiState, lineColor)
        val initialPositionOffset = calculateOffset(uiState.plateauSize, uiState.initialPosition)
        drawCircle(
            color = primaryColor,
            radius = 15f,
            center = initialPositionOffset,
        )
    }
}

@Composable
fun PlateauCanvasLegend(primaryColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Spacer(
            Modifier
                .size(8.dp)
                .drawBehind {
                    drawCircle(color = primaryColor)
                })
        Text(stringResource(R.string.initial_position), style = MaterialTheme.typography.labelSmall)
    }
}

private fun DrawScope.calculateOffset(plateauSize: Int, position: Coordinates) = Offset(
    x = position.x * (size.width / plateauSize),
    y = (plateauSize - position.y) * (size.height / plateauSize),
)

private fun DrawScope.drawPlateauGrid(
    uiState: MovementsUiState, lineColor: Color
) {
    for (i in 0..uiState.plateauSize) {
        val x = i * (size.width / uiState.plateauSize)
        drawLine(
            color = lineColor,
            start = Offset(x = x, y = 0f),
            end = Offset(x = x, y = size.height),
        )
        val y = i * (size.height / uiState.plateauSize)
        drawLine(
            color = lineColor,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
        )
    }
}