package yacekbass.directsheet.render.shape

import java.awt.geom.Line2D
import java.awt.geom.Path2D
import java.awt.geom.Rectangle2D

class StaffShape(x : Float, y : Float, length: Float, val lineCount : Int, val staffLineSep : Float)
    : Path2DDelegatingShape() {

    private val lines : Path2D.Float = Path2D.Float()
    override val path2D: Path2D = lines

    init {
        for (i in 1..lineCount) {
            val lineY = y + (i-1) * staffLineSep
            lines.moveTo(x, lineY)
            lines.lineTo(x + length, lineY)
        }
    }

    /**
     * Returns a target rectangle for a symbol to draw on a specific line on this staff and relative x-coordinate.
     * Both width and height values of returned rectangle is equal to staff lines' separation.
     */
    fun symbolBounds(relativeXpos: Float, lineNumber: Int): Rectangle2D.Float {
        val x = (bounds2D.x + relativeXpos).toFloat()
        val y = (bounds2D.y + lineNumber * (staffLineSep / 2f)).toFloat()
        val height = staffLineSep
        return Rectangle2D.Float(x, y, height, height)
    }

    /**
     * Returns path representing ledger lines required to draw a symbol on a given line number.
     * @param lineNumber
     * LineNumber = 0 represents the top line of a staff, lineNumber = 1 represents a space between
     * the top line and the second line, lineNumber = 2 represents the second line from the top, and so forth.
     *
     * Negative values represent location above the staff.
     */
    fun ledgerLines(symbolWidth: Float, relativeXpos: Float, lineNumber: Int): Path2D.Float {
        val ledgerLineX = bounds2D.x.toFloat() + relativeXpos - 0.1f * staffLineSep
        val ledgerLineWidth = symbolWidth + staffLineSep * 0.2f
        val path: Path2D.Float
        path = when {
            lineNumber < -1 ->
                createLedgerLinesAbove(ledgerLineX, ledgerLineWidth, -lineNumber / 2)
            lineNumber >= lineCount * 2 - 2 ->
                createLedgerLinesBelow(ledgerLineX, ledgerLineWidth, (lineNumber - lineCount * 2 + 2) / 2)
            else -> Path2D.Float()
        }
        return path
    }

    private fun createLedgerLinesAbove(x : Float, width : Float, count: Int) : Path2D.Float {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), bounds2D.y - i * staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        return path
    }

    private fun createLedgerLinesBelow(x : Float, width : Float, count: Int): Path2D.Float {
        val path = Path2D.Float()
        for (i in 1..count) {
            path.moveTo(x.toDouble(), bounds2D.y + bounds2D.height + i*staffLineSep)
            path.lineTo(x + width, path.currentPoint.y.toFloat())
        }
        return path
    }

    /**
     * Returns a path representing a single vertical line across all staff lines, at a specified x-coordinate
     */
    fun barLine(relativeXpos: Float): Line2D.Float {
        return Line2D.Float(bounds2D.x.toFloat() + relativeXpos,
                bounds2D.y.toFloat(),
                bounds2D.x.toFloat() + relativeXpos,
                bounds2D.y.toFloat() + staffLineSep * (lineCount - 1))
    }
}
