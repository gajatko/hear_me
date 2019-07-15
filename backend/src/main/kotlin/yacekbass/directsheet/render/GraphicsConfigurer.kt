package yacekbass.directsheet.render

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Font.TRUETYPE_FONT
import java.awt.Graphics2D

class GraphicsConfigurer(val g: Graphics2D, val config: SpacingConfig) {

    private val musicFont: Font

    init {
        val inputStream = javaClass
                .classLoader.getResourceAsStream("emmentaler.ttf")
        musicFont = Font.createFont(TRUETYPE_FONT, inputStream)
    }

    fun basicStroke()
    {
        g.stroke = BasicStroke(config.staffLineWidth)
        g.paint = Color.BLACK
    }

    fun background() {
        g.background = Color.WHITE
    }

    fun musicFont() {
        g.font = musicFont.deriveFont(config.staffLineSep * 4f)
    }
}
