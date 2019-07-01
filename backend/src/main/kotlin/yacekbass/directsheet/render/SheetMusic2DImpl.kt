package yacekbass.directsheet.render

import yacekbass.directsheet.Positioner
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D

open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    val config : SpacingConfig = SpacingConfig()
    val positioner : Positioner = Positioner(config)
    private val graphicsConfigurer : GraphicsConfigurer = GraphicsConfigurer(g, config)

    override fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int)
    : Rectangle2D.Float {
        graphicsConfigurer.staffLine()
        for (i in 1..lineCount) {
            val lineY = y + (i-1)*config.staffLineSep
            g.draw(Line2D.Float(x, lineY, x, x+length))
        }
        return Rectangle2D.Float(x, y, length, y + (lineCount-1)*config.staffLineSep)
    }

    override fun drawNote(staffRect : Rectangle2D.Float, xpos: Float, lineNumber: Int) {
        graphicsConfigurer.staffLine()

        val half = config.staffLineSep / 2f
        val centerX = staffRect.x + xpos - half
        val centerY = staffRect.y + lineNumber * half

        g.draw(Ellipse2D.Float(centerX - half, centerY - half, half * 2f, half * 2f))
    }
}
