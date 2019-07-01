package yacekbass.directsheet.render

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

class GraphicsConfigurer(val svg: Graphics2D, val config: SpacingConfig) {

    fun staffLine()
    {
        svg.stroke = BasicStroke(config.staffLineWidth)
        svg.paint = Color.BLACK
    }

    fun background() {
        svg.background = Color.WHITE
    }
}
