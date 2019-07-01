package yacekbass.directsheet.render

import java.awt.*
import java.awt.font.FontRenderContext
import java.awt.font.GlyphVector
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.awt.image.BufferedImageOp
import java.awt.image.ImageObserver
import java.awt.image.RenderedImage
import java.awt.image.renderable.RenderableImage
import java.text.AttributedCharacterIterator
import kotlin.math.roundToInt

open class DelegatingGraphics2D(private val graphics : Graphics2D) : Graphics2D() {

    override fun getClipBounds(): Rectangle {
        return graphics.clipBounds
    }

    override fun drawPolyline(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        graphics.drawPolyline(xPoints, yPoints, nPoints)
    }

    override fun rotate(theta: Double) {
        throw UnsupportedOperationException()
    }

    override fun rotate(theta: Double, x: Double, y: Double) {
        throw UnsupportedOperationException()
    }

    override fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        graphics.drawLine(x1, y1, x2, y2)
    }

    override fun copyArea(x: Int, y: Int, width: Int, height: Int, dx: Int, dy: Int) {
        graphics.copyArea(x, y, width, height, dx, dy)
    }

    override fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        graphics.fillArc(x, y, width, height, startAngle, arcAngle)
    }

    override fun clip(s: Shape?) {
        graphics.clip = s
    }

    override fun drawString(str: String?, x: Int, y: Int) {
        graphics.drawString(str, x, y)
    }

    override fun drawString(str: String?, x: Float, y: Float) {
        graphics.drawString(str, toDeviceX(x), toDeviceY(y))
    }

    override fun drawString(iterator: AttributedCharacterIterator?, x: Int, y: Int) {
        graphics.drawString(iterator, x, y)
    }

    override fun drawString(iterator: AttributedCharacterIterator?, x: Float, y: Float) {
        graphics.drawString(iterator, toDeviceX(x), toDeviceY(y))
    }

    override fun clipRect(x: Int, y: Int, width: Int, height: Int) {
        graphics.clipRect(x, y, width, height)
    }

    override fun setPaintMode() {
        graphics.setPaintMode()
    }

    override fun getColor(): Color {
        return graphics.color
    }

    override fun scale(sx: Double, sy: Double) {
        scale(sx, sy)
    }

    override fun drawImage(img: Image?, x: Int, y: Int, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, x, y, observer)
    }

    override fun drawImage(img: Image?, x: Int, y: Int, width: Int, height: Int, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, x, y, width, height, observer)
    }

    override fun drawImage(img: Image?, x: Int, y: Int, bgcolor: Color?, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, x, y, bgcolor, observer)
    }

    override fun drawImage(img: Image?, x: Int, y: Int, width: Int, height: Int, bgcolor: Color?, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, x, y, width, height, bgcolor, observer)
    }

    override fun drawImage(img: Image?, dx1: Int, dy1: Int, dx2: Int, dy2: Int, sx1: Int, sy1: Int, sx2: Int, sy2: Int, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
    }

    override fun drawImage(img: Image?, dx1: Int, dy1: Int, dx2: Int, dy2: Int, sx1: Int, sy1: Int, sx2: Int, sy2: Int, bgcolor: Color?, observer: ImageObserver?): Boolean {
        return graphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
    }

    override fun setXORMode(c1: Color?) {
        return graphics.setXORMode(c1)
    }

    override fun translate(x: Int, y: Int) {
        graphics.translate(x, y)
    }

    override fun translate(tx: Double, ty: Double) {
        graphics.translate(toDeviceX(tx), toDeviceY(ty))
    }

    override fun setFont(font: Font?) {
        graphics.font = font
    }

    override fun getFont(): Font {
        return graphics.font
    }

    override fun fillOval(x: Int, y: Int, width: Int, height: Int) {
        graphics.fillOval(x, y, width, height)
    }

    override fun getClip(): Shape {
        return graphics.clip
    }

    override fun dispose() {
        graphics.dispose()
    }

    override fun setClip(x: Int, y: Int, width: Int, height: Int) {
        graphics.setClip(x, y, width, height)
    }

    override fun setClip(clip: Shape?) {
        graphics.clip = clip
    }

    override fun create(): Graphics {
        return graphics.create()
    }

    override fun drawOval(x: Int, y: Int, width: Int, height: Int) {
        graphics.drawOval(x, y, width, height)
    }

    override fun clearRect(x: Int, y: Int, width: Int, height: Int) {
        graphics.clearRect(x, y, width, height)
    }

    override fun drawPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        graphics.drawPolygon(xPoints, yPoints, nPoints)
    }

    override fun fillRect(x: Int, y: Int, width: Int, height: Int) {
        graphics.fillRect(x, y, width, height)
    }

    override fun drawRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        graphics.drawRoundRect(x, y, width, height, arcWidth, arcHeight)
    }

    override fun getFontMetrics(f: Font?): FontMetrics {
        return graphics.getFontMetrics(f)
    }

    override fun fillPolygon(xPoints: IntArray?, yPoints: IntArray?, nPoints: Int) {
        graphics.fillPolygon(xPoints, yPoints, nPoints)
    }

    override fun setColor(c: Color?) {
        graphics.color = c
    }

    override fun fillRoundRect(x: Int, y: Int, width: Int, height: Int, arcWidth: Int, arcHeight: Int) {
        graphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight)
    }

    override fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        graphics.drawArc(x, y, width, height, startAngle, arcAngle)
    }
    override fun drawRenderableImage(img: RenderableImage?, xform: AffineTransform?) {
        graphics.drawRenderableImage(img, xform)
    }

    override fun setComposite(comp: Composite?) {
        graphics.composite = comp
    }

    override fun setTransform(Tx: AffineTransform?) {
        graphics.transform = Tx
    }

    override fun getPaint(): Paint = graphics.paint

    override fun drawGlyphVector(g: GlyphVector?, x: Float, y: Float) {
        graphics.drawGlyphVector(g, x, y)
    }

    override fun setRenderingHint(hintKey: RenderingHints.Key?, hintValue: Any?) {
        graphics.setRenderingHint(hintKey, hintValue)
    }

    override fun getRenderingHint(hintKey: RenderingHints.Key?): Any {
        return graphics.getRenderingHint(hintKey)
    }

    override fun hit(rect: Rectangle?, s: Shape?, onStroke: Boolean): Boolean {
        return graphics.hit(rect, s, onStroke)
    }

    override fun setBackground(color: Color?) {
        graphics.background = color
    }

    open fun toDeviceY(y: Double): Int {
        return y.roundToInt()
    }

    fun toDeviceX(x: Double): Int {
        return x.roundToInt()
    }

    fun toDeviceY(y: Float): Int {
        return y.roundToInt()
    }

    fun toDeviceX(x: Float): Int {
        return x.roundToInt()
    }

    override fun drawImage(img: Image?, xform: AffineTransform?, obs: ImageObserver?): Boolean {
        return graphics.drawImage(img, xform, obs)
    }

    override fun drawImage(img: BufferedImage?, op: BufferedImageOp?, x: Int, y: Int) {
        graphics.drawImage(img, op, x, y)
    }

    override fun draw(s: Shape?) {
        graphics.draw(s)
    }

    override fun setStroke(s: Stroke?) {
        graphics.stroke = s
    }

    override fun getComposite(): Composite {
        return graphics.composite
    }

    override fun fill(s: Shape?) {
        graphics.fill(s)
    }

    override fun getDeviceConfiguration(): GraphicsConfiguration = graphics.deviceConfiguration

    override fun getBackground(): Color = graphics.background

    override fun setPaint(paint: Paint?) {
        graphics.paint = paint
    }

    override fun shear(shx: Double, shy: Double) {
        graphics.shear(shx, shy)
    }

    override fun transform(Tx: AffineTransform?) {
        graphics.transform(Tx)
    }

    override fun getFontRenderContext(): FontRenderContext = graphics.fontRenderContext

    override fun addRenderingHints(hints: MutableMap<*, *>?) {
        graphics.addRenderingHints(hints)
    }

    override fun getRenderingHints(): RenderingHints = graphics.renderingHints

    override fun getStroke(): Stroke = graphics.stroke

    override fun drawRenderedImage(img: RenderedImage?, xform: AffineTransform?) {
        graphics.drawRenderedImage(img, xform)
    }

    override fun setRenderingHints(hints: MutableMap<*, *>?) {
        graphics.setRenderingHints(hints)
    }

    override fun getTransform(): AffineTransform = graphics.transform

}