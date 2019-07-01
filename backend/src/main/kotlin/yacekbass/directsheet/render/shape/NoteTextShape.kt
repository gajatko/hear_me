package yacekbass.directsheet.render.shape

import java.awt.geom.Path2D

class NoteTextShape(staff: StaffShape, xPos : Float, lineNumber : Int) : Path2DDelegatingShape() {

    private val lines : Path2D.Float = Path2D.Float()
    override val path2D: Path2D = lines

    init {
        val rect = staff.bounds2D
        val sep = staff.staffLineSep
        val half = sep / 2f
        val x = rect.x + xPos
        val y = rect.y + lineNumber * half
        lines.moveTo(x, y)
        lines.curveTo(
                x + 0.5 * sep, y,
                x + sep, y + 0.5 * sep,
                x + sep, y + sep)
        lines.curveTo(
                x + 0.5 * sep, y + sep,
                x, y + 0.5 * sep,
                x, y)
        lines.closePath()
    }

}
