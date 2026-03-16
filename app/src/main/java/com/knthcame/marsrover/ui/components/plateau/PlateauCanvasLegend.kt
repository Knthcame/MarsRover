package com.knthcame.marsrover.ui.components.plateau

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.knthcame.marsrover.R

@Composable
fun PlateauCanvasLegend() {
    val initialPositionColor = initialPositionColor
    val lineColor = lineColor

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End,
        modifier = Modifier.fillMaxWidth(),
    ) {
        InitialPositionLegend(initialPositionColor)
        PredictedPathLegend(initialPositionColor, lineColor)
        RoverIconLegend()
    }
}

@Composable
private fun InitialPositionLegend(initialPositionColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            stringResource(R.string.initial_position),
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(
            Modifier
                .size(8.dp)
                .drawBehind {
                    drawCircle(color = initialPositionColor)
                },
        )
    }
}

@Composable
private fun PredictedPathLegend(initialPositionColor: Color, lineColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(stringResource(R.string.rover_path), style = MaterialTheme.typography.labelSmall)
        Spacer(
            Modifier
                .size(8.dp)
                .drawBehind {
                    drawLine(
                        color = initialPositionColor,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = PLATEAU_PATH_STROKE_WIDTH,
                        colorFilter = ColorFilter.tint(lineColor),
                    )
                },
        )
    }
}

@Composable
private fun RoverIconLegend() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            stringResource(R.string.rover_position_and_direction),
            style = MaterialTheme.typography.labelSmall,
        )
        val roverColor = roverColor
        val roverIcon = roverIcon
        val roverSize = 12.dp
        Spacer(
            Modifier
                .size(roverSize)
                .drawBehind {
                    drawImage(
                        image = roverIcon,
                        colorFilter = ColorFilter.tint(roverColor),
                        dstSize = IntSize(
                            width = roverSize.toPx().toInt(),
                            height = roverSize.toPx().toInt(),
                        ),
                    )
                },
        )
    }
}
