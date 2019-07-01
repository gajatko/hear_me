package yacekbass.directsheet.render.shape

import java.awt.geom.AffineTransform
import java.awt.geom.PathIterator
import java.awt.geom.Rectangle2D
import java.awt.geom.RectangularShape

class Staff : RectangularShape() {
    override fun contains(x: Double, y: Double): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBounds2D(): Rectangle2D {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getX(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getHeight(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getY(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPathIterator(at: AffineTransform?): PathIterator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getWidth(): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFrame(x: Double, y: Double, w: Double, h: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun Staff(xPos: kotlin.Float, yPos: kotlin.Float, length: kotlin.Float, lineCount: Int) {
        super.x = xPos
        super.y = yPos
        super.width = length
        super.height
    }

}
