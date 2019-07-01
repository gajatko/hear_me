package yacekbass.directsheet.render.shape

import java.awt.geom.Path2D

class Staff(x : Float, y : Float, length: Float, val lineCount : Int, val staffLineSep : Float) : Path2DDelegatingShape() {

    private val lines : Path2D.Float = Path2D.Float()
    override val path2D: Path2D = lines

    init {
        for (i in 1..lineCount) {
            val lineY = y + (i-1) * staffLineSep
            lines.moveTo(x, lineY)
            lines.lineTo(x + length, lineY)
        }
    }

}
