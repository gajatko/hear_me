package yacekbass.directsheet.render.shape

import java.awt.Rectangle
import java.awt.Shape
import java.awt.geom.*

abstract class Path2DDelegatingShape : Shape
{
    protected abstract val path2D: Path2D

    override fun contains(p: Point2D?): Boolean = path2D.contains(p)

    override fun contains(r: Rectangle2D?): Boolean = path2D.contains(r)

    override fun getPathIterator(at: AffineTransform?, flatness: Double): PathIterator = getPathIterator(at)

    override fun intersects(r: Rectangle2D?): Boolean = path2D.intersects(r)

    override fun getBounds(): Rectangle = path2D.bounds

    override fun contains(x: Double, y: Double): Boolean = path2D.contains(x, y)

    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean = path2D.contains(x, y, w, h)

    override fun getBounds2D(): Rectangle2D = path2D.bounds2D

    override fun getPathIterator(at: AffineTransform?): PathIterator = path2D.getPathIterator(at)

    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean = path2D.intersects(x, y, w, h)

}