package yacekbass.directsheet.render

import yacekbass.directsheet.Positioner
import yacekbass.directsheet.render.shape.Note
import yacekbass.directsheet.render.shape.Staff
import java.awt.Graphics2D

open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    val spacing : SpacingConfig = SpacingConfig()
    val positioner : Positioner = Positioner(spacing)
    private val c : GraphicsConfigurer = GraphicsConfigurer(g, spacing)

    init {
        c.background()
    }

    override fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int) : Staff {
        c.staffLine()
        val staff = Staff(x, y, length, lineCount, spacing.staffLineSep)
        g.draw(staff)
        return staff
    }

    override fun drawNote(staff : Staff, xpos: Float, lineNumber: Int) {
        c.staffLine()
        val note = Note(staff, xpos, lineNumber)
        g.fill(note)
    }
}
