package yacekbass.directsheet.render

import java.awt.Graphics2D
import java.awt.geom.Rectangle2D

abstract class SheetMusic2D(graphics : Graphics2D) : DelegatingGraphics2D(graphics) {
    abstract fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int): Rectangle2D.Float
    abstract fun drawNote(staffRect: Rectangle2D.Float, xpos: Float, lineNumber: Int)
}
