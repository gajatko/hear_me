package yacekbass.directsheet.render

import java.awt.Shape

interface SheetMusic2DVisitor {
    fun visit(g: SheetMusic2D, spacing : SpacingConfig) : Shape
}
