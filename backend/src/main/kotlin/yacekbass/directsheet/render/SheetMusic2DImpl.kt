package yacekbass.directsheet.render

import yacekbass.directsheet.render.shape.NoteShape
import yacekbass.directsheet.render.shape.StaffShape
import java.awt.Graphics2D
import java.awt.geom.Path2D

open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    private val spacing : SpacingConfig = SpacingConfig()
    private val c : GraphicsConfigurer = GraphicsConfigurer(g, spacing)
    private val sep = spacing.staffLineSep

    init {
        c.background()
        c.musicFont()
        c.staffLine()
    }

    override fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int) : StaffShape {
        val staff = StaffShape(x, y, length, lineCount, sep)
        g.draw(staff)
        return staff
    }

    private fun drawLedgerLinesAbove(staff : StaffShape, x : Float, width : Float, count: Int) {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), staff.bounds2D.y - i * staff.staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        g.draw(path)
    }

    private fun drawLedgerLinesBelow(staff : StaffShape, x : Float, width : Float, count: Int) {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), staff.bounds2D.y + staff.bounds2D.height + i*staff.staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        g.draw(path)
    }

    override fun drawNote(staff : StaffShape, xpos: Float, lineNumber: Int) {
        val note = NoteShape(staff, xpos, lineNumber)
        val ledgerLineX = staff.bounds2D.x.toFloat() + xpos - 0.1f * sep
        if (lineNumber < -1) {
            drawLedgerLinesAbove(staff, ledgerLineX,
                    spacing.ledgerLineWidth,
                    -lineNumber / 2)
        } else if (lineNumber >= staff.lineCount * 2 - 2) {
            drawLedgerLinesBelow(staff, ledgerLineX,
                    spacing.ledgerLineWidth,
                    (lineNumber - staff.lineCount * 2 + 2) / 2)
        }
        g.drawString("w", note.bounds2D.x.toFloat(), note.bounds2D.y.toFloat())
    }
}
