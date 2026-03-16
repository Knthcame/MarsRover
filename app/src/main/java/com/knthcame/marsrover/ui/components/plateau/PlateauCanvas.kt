package com.knthcame.marsrover.ui.components.plateau

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.data.control.models.CardinalDirection
import com.knthcame.marsrover.data.control.models.Coordinates
import com.knthcame.marsrover.data.control.models.Position
import com.knthcame.marsrover.ui.theme.MarsRoverTheme

@Composable
fun PlateauCanvas(
    topRightCorner: Coordinates,
    positions: List<Position>,
    modifier: Modifier = Modifier,
) {
    val gridLineColor = lineColor
    val pathColor = lineColor
    val initialPositionColor = initialPositionColor
    val roverColor = roverColor
    val roverIcon = roverIcon

    Canvas(modifier.aspectRatio(topRightCorner.x.toFloat() / topRightCorner.y)) {
        drawPlateauGrid(topRightCorner, gridLineColor)

        if (positions.size > 1) {
            drawRoverPath(topRightCorner, positions, pathColor)
            drawInitialPosition(topRightCorner, positions, initialPositionColor)
        }

        drawRover(
            position = positions.last(),
            topRightCorner = topRightCorner,
            icon = roverIcon,
            color = roverColor,
        )
    }
}

private fun DrawScope.drawRover(
    position: Position,
    topRightCorner: Coordinates,
    icon: ImageBitmap,
    color: Color,
) {
    val roverImageSize = 24.dp.toPx()
    val offset = calculateOffset(
        topRightCorner = topRightCorner,
        position = position.roverPosition,
    )

    @Suppress("MagicNumber")
    val rotation = when (position.roverDirection) {
        CardinalDirection.North -> 0f
        CardinalDirection.East -> 90f
        CardinalDirection.South -> 180f
        CardinalDirection.West -> 270f
    }

    rotate(degrees = rotation, pivot = offset) {
        drawImage(
            icon,
            dstSize = IntSize(roverImageSize.toInt(), roverImageSize.toInt()),
            dstOffset = IntOffset(
                (offset.x - roverImageSize / 2).toInt(),
                (offset.y - roverImageSize / 2).toInt(),
            ),
            colorFilter = ColorFilter.tint(color),
        )
    }
}

private fun DrawScope.drawRoverPath(
    topRightCorner: Coordinates,
    positions: List<Position>,
    color: Color,
) {
    val initialOffset = calculateOffset(topRightCorner, positions.first().roverPosition)
    val path = Path().apply {
        moveTo(initialOffset.x, initialOffset.y)
    }
    for (i in 1..positions.lastIndex) {
        val position = positions[i]
        val offset = calculateOffset(topRightCorner, position.roverPosition)
        path.lineTo(offset.x, offset.y)
    }
    drawPath(path, color, style = Stroke(width = PLATEAU_PATH_STROKE_WIDTH))
}

private fun DrawScope.drawInitialPosition(
    topRightCorner: Coordinates,
    positions: List<Position>,
    primaryColor: Color,
) {
    val initialPositionOffset = calculateOffset(
        topRightCorner = topRightCorner,
        position = positions.first().roverPosition,
    )
    drawCircle(
        color = primaryColor,
        radius = 15f,
        center = initialPositionOffset,
    )
}

// topRightCorner of (0,0) means the bottom-left corner.
// While and offset of (0,0) means the top-left corner.
private fun DrawScope.calculateOffset(topRightCorner: Coordinates, position: Coordinates) = Offset(
    x = position.x * (size.width / topRightCorner.x),
    y = (topRightCorner.y - position.y) * (size.height / topRightCorner.y),
)

private fun DrawScope.drawPlateauGrid(topRightCorner: Coordinates, lineColor: Color) {
    for (i in 0..topRightCorner.x) {
        val x = i * (size.width / topRightCorner.x)
        drawLine(
            color = lineColor,
            start = Offset(x = x, y = 0f),
            end = Offset(x = x, y = size.height),
        )
    }
    for (i in 0..topRightCorner.y) {
        val y = i * (size.height / topRightCorner.y)
        drawLine(
            color = lineColor,
            start = Offset(x = 0f, y = y),
            end = Offset(x = size.width, y = y),
        )
    }
}

@Composable
@Preview
fun PlateauCanvasSetupPreview() {
    MarsRoverTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PlateauCanvas(
                    topRightCorner = Coordinates(5, 7),
                    positions = listOf(Position(Coordinates(3, 2), CardinalDirection.North)),
                    modifier = Modifier.heightIn(max = 300.dp),
                )

                PlateauCanvasLegend()
            }
        }
    }
}

@Composable
@Preview
fun PlateauCanvasMovementsPreview() {
    MarsRoverTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PlateauCanvas(
                    topRightCorner = Coordinates(7, 5),
                    positions = listOf(
                        Position(Coordinates(3, 2), CardinalDirection.North),
                        Position(Coordinates(3, 3), CardinalDirection.North),
                        Position(Coordinates(3, 3), CardinalDirection.East),
                        Position(Coordinates(4, 3), CardinalDirection.East),
                        Position(Coordinates(4, 3), CardinalDirection.South),
                        Position(Coordinates(4, 2), CardinalDirection.South),
                    ),
                    modifier = Modifier.heightIn(max = 300.dp),
                )

                PlateauCanvasLegend()
            }
        }
    }
}
