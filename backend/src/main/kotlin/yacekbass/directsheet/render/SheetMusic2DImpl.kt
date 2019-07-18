package yacekbass.directsheet.render

import yacekbass.directsheet.render.shape.StaffShape
import java.awt.Graphics2D
import java.awt.Shape


open class SheetMusic2DImpl(private val g: Graphics2D) : SheetMusic2D(g) {

    private val spacing : SpacingConfig = SpacingConfig()
    private val c : GraphicsConfigurer = GraphicsConfigurer(g, spacing)

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

    override fun drawSymbol(staff : StaffShape, symbol: FontChars, xpos: Float, lineNumber: Int): Shape {
        val symbolBounds = staff.symbolLocation(xpos, lineNumber)
        val font = when {
            symbol.sizeFix != null -> g.font.deriveFont(staff.bounds2D.height.toFloat() * symbol.sizeFix)
            else -> g.font.deriveFont(staff.bounds2D.height.toFloat())
        }
        val v = font.createGlyphVector(g.fontRenderContext, CharArray(1) { symbol.charCode.toChar()})
        if (symbol.flags.contains(FontCharFlags.REQUIRES_LEDGER_LINES)) {
            g.draw(staff.ledgerLines(v.visualBounds.width.toFloat(), xpos, lineNumber))
        }
        g.translate(symbolBounds.x.toDouble(), symbolBounds.y.toDouble())
        g.fill(v.outline)
        g.translate(-symbolBounds.x.toDouble(), -symbolBounds.y.toDouble())
        return v.outline
    }

    override fun accept(visitor: SheetMusic2DVisitor): Shape {
        val shape = visitor.visit(this, spacing)
        g.fill(shape)
        return shape
    }
}
