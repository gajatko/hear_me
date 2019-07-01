package yacekbass.directsheet.render

import yacekbass.directsheet.render.shape.Staff
import java.awt.Graphics2D

abstract class SheetMusic2D(graphics : Graphics2D) : DelegatingGraphics2D(graphics) {
    abstract fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int): Staff
    abstract fun drawNote(staff : Staff, xpos: Float, lineNumber: Int)
}
