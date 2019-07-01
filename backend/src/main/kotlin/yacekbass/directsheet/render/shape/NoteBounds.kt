package yacekbass.directsheet.render.shape

class NoteBounds(staff: StaffShape, xPos : Float, lineNumber : Int) {

    val x: Float
    val y: Float
    private val height: Float

    init {
        val rect = staff.bounds2D
        val sep = staff.staffLineSep
        val half = sep / 2f
        x = (rect.x + xPos).toFloat()
        y = (rect.y + lineNumber * half).toFloat()
        height = sep
    }

}
