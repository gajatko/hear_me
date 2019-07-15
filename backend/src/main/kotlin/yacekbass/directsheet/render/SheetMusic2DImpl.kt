package yacekbass.directsheet.render

import yacekbass.directsheet.render.shape.NoteBounds
import yacekbass.directsheet.render.shape.StaffShape
import java.awt.Graphics2D
import java.awt.geom.Path2D



open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    private val spacing : SpacingConfig = SpacingConfig()
    private val c : GraphicsConfigurer = GraphicsConfigurer(g, spacing)
    var currentSymbol = FontChars.WHOLE_NOTE

    init {
        initPaint()
    }

    private fun initPaint() {
        c.background()
        c.musicFont()
        c.basicStroke()
    }

    override fun drawStaff(x : Float, y : Float, length: Float, lineCount : Int) : StaffShape {
        val staff = StaffShape(x, y, length, lineCount, spacing.staffLineSep)
        g.draw(staff)
        return staff
    }

    override fun drawSymbol(staff : StaffShape, xpos: Float, lineNumber: Int) {
        val note = NoteBounds(staff, xpos, lineNumber)
        val font = if (currentSymbol.sizeFix != null)
            g.font.deriveFont(staff.staffLineSep * currentSymbol.sizeFix!!)
        else
            g.font
        val v = font.createGlyphVector(g.fontRenderContext, CharArray(1) { i -> currentSymbol.charCode.toChar()})
        drawLedgerLinesIfNecessary(v.visualBounds.width.toFloat(), staff, xpos, lineNumber)
        g.translate(note.x.toDouble(), note.y.toDouble())
        g.fill(v.outline)
        g.translate(-note.x.toDouble(), -note.y.toDouble())
    }

    private fun drawLedgerLinesIfNecessary(symbolWidth: Float, staff: StaffShape, xpos: Float, lineNumber: Int) {
        val ledgerLineX = staff.bounds2D.x.toFloat() + xpos - 0.1f * staff.staffLineSep
        val ledgerLineWidth = symbolWidth + staff.staffLineSep * 0.2f
        if (lineNumber < -1) {
            val path = createLedgerLinesAbove(staff, ledgerLineX, ledgerLineWidth,
                    -lineNumber / 2)
            g.draw(path)
        } else if (lineNumber >= staff.lineCount * 2 - 2) {
            val path = createLedgerLinesBelow(staff, ledgerLineX, ledgerLineWidth,
                    (lineNumber - staff.lineCount * 2 + 2) / 2)
            g.draw(path)
        }
    }

    private fun createLedgerLinesAbove(staff : StaffShape, x : Float, width : Float, count: Int) : Path2D.Float {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), staff.bounds2D.y - i * staff.staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        return path
    }

    private fun createLedgerLinesBelow(staff : StaffShape, x : Float, width : Float, count: Int): Path2D.Float {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), staff.bounds2D.y + staff.bounds2D.height + i*staff.staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        return path
    }

}
