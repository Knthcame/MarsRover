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
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Instructions

@Composable
fun PlateauCanvas(
    instructions: Instructions,
    primaryColor: Color,
    modifier: Modifier = Modifier,
) {
    val lineColor = MaterialTheme.colorScheme.onBackground

    Canvas(
        modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
    ) {
        drawPlateauGrid(instructions, lineColor)
        val initialPositionOffset = calculateOffset(
            topRightCorner = instructions.topRightCorner,
            position = instructions.roverPosition,
        )
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

// topRightCorner of (0,0) means the bottom-left corner.
// While and offset of (0,0) means the top-left corner.
private fun DrawScope.calculateOffset(topRightCorner: Coordinates, position: Coordinates) = Offset(
    x = position.x * (size.width / topRightCorner.x),
    y = (topRightCorner.y - position.y) * (size.height / topRightCorner.y),
)

private fun DrawScope.drawPlateauGrid(
    instructions: Instructions,
    lineColor: Color
) {
    for (i in 0..instructions.topRightCorner.x) {
        val x = i * (size.width / instructions.topRightCorner.x)
        drawLine(
            color = lineColor,
            start = Offset(x = x, y = 0f),
            end = Offset(x = x, y = size.height),
        )
    }
    for (i in 0..instructions.topRightCorner.y) {
        val y = i * (size.height / instructions.topRightCorner.y)
        drawLine(
            color = lineColor,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
        )
    }
}