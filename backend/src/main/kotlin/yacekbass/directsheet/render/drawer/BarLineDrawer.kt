package yacekbass.directsheet.render.drawer

import yacekbass.directsheet.render.SheetMusic2D
import yacekbass.directsheet.render.SheetMusic2DVisitor
import yacekbass.directsheet.render.SpacingConfig
import yacekbass.directsheet.render.drawer.BarLineDrawer.BarLineStyle.*
import yacekbass.directsheet.render.shape.StaffShape
import java.awt.BasicStroke
import java.awt.Shape
import java.awt.geom.Area
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D

class BarLineDrawer(private val staff: StaffShape, private val xPos: Float, private val styles: List<BarLineStyle>) : SheetMusic2DVisitor {

    companion object {
        fun simple() : List<BarLineStyle> {
            return listOf(Thin)
        }
        fun double() : List<BarLineStyle> {
            return listOf(Thin, Space, Thin)
        }
        fun pieceEnd() : List<BarLineStyle> {
            return listOf(Thin, DoubleSpace, Thick)
        }
        fun repeatStart() : List<BarLineStyle> {
            return listOf(Thick, DoubleSpace, Thin, Space, RepeatDots)
        }
        fun repeatEnd() : List<BarLineStyle> {
            return listOf(RepeatDots, Space, Thin, DoubleSpace, Thick)
        }
    }

    override fun visit(g: SheetMusic2D, spacing: SpacingConfig) : Shape {
        var x = xPos
        val barLineArea = Area()
        for (style in styles) {
            when {
                style == RepeatDots -> {
                    val dot1 = makeRepeatDot(staff.symbolBounds(x, 3))
                    val dot2 = makeRepeatDot(staff.symbolBounds(x, 5))
                    barLineArea.add(Area(dot1))
                    barLineArea.add(Area(dot2))
                    x += dot1.width
                }
                style.isLineStyle -> {
                    val stroke = makeStroke(spacing, style)
                    val width = stroke.lineWidth
                    x += width/2
                    val bar = staff.barLine(x)
                    val line = Line2D.Float(bar.x1, bar.y1 + width/2, bar.x2, bar.y2 - width/2)
                    barLineArea.add(Area(stroke.createStrokedShape(line)))
                    x += width/2
                }
                style == Space -> x += 2 * spacing.staffLineWidth
                style == DoubleSpace -> x += 4 * spacing.staffLineWidth
            }
        }
        return barLineArea
    }

    private fun makeRepeatDot(sBounds: Rectangle2D.Float) =
            Ellipse2D.Float(sBounds.x, sBounds.y + sBounds.height / 4f, sBounds.height / 2f, sBounds.height / 2f)

    private fun makeStroke(spacing: SpacingConfig, style: BarLineStyle): BasicStroke {
        val s = spacing.staffLineWidth
        val l = spacing.staffLineSep
        return when (style) {
            Thin -> BasicStroke(s, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)
            Thick -> BasicStroke(s*5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)
            Dots -> BasicStroke(s, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,
                    1f, floatArrayOf(l/6, l/6), 0f)
            Dashes -> BasicStroke(s, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,
                    1f, floatArrayOf(l*0.6f, l*0.6f), 0f)
            else -> throw IllegalArgumentException()
        }
    }
    enum class BarLineStyle(val isLineStyle: Boolean = false) {
        Thin(true),
        Thick(true),
        Dots(true),
        Dashes(true),
        Space,
        DoubleSpace,
        RepeatDots
    }
}

