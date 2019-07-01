package yacekbass.directsheet.render

import yacekbass.directsheet.Positioner
import yacekbass.directsheet.render.shape.NoteShape
import yacekbass.directsheet.render.shape.StaffShape
import java.awt.Graphics2D

open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    val spacing : SpacingConfig = SpacingConfig()
    val positioner : Positioner = Positioner(spacing)
    private val c : GraphicsConfigurer = GraphicsConfigurer(g, spacing)

    init {
        c.background()
        c.musicFont()
        c.staffLine()
    }

    override fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int) : StaffShape {
        val staff = StaffShape(x, y, length, lineCount, spacing.staffLineSep)
        g.draw(staff)
        return staff
    }

    override fun drawNote(staff : StaffShape, xpos: Float, lineNumber: Int) {
        val note = NoteShape(staff, xpos, lineNumber)
        g.fill(note)
    }
    fun drawNoteFont(staff : StaffShape, xpos: Float, lineNumber: Int) {
        val note = NoteShape(staff, xpos, lineNumber)
        g.drawString("w", note.bounds2D.x.toFloat(), note.bounds2D.y.toFloat())
    }
}
