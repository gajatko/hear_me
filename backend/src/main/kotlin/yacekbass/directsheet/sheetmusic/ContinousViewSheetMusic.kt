package yacekbass.directsheet.sheetmusic;

import org.jfree.graphics2d.svg.SVGGraphics2D
import yacekbass.directsheet.render.FontChars
import yacekbass.directsheet.render.SheetMusic2DImpl
import yacekbass.directsheet.render.shape.BarLineDrawer
import java.io.FileWriter
import java.nio.file.Files

class ContinousViewSheetMusic(w: Int, h: Int) {

    val svg = SVGGraphics2D(w, h)
    val g = SheetMusic2DImpl(svg)

    fun draw()
    {
        val staff = g.drawStaff(10f, 100f, 800f, 5)
        var x = 0f
        g.drawSymbol(staff, FontChars.G_CLEF, x, 6)
        x+=30f
        g.drawSymbol(staff, FontChars.C_CLEF, x, 4)
//        g.color = Color.BLUE
        x+=30f
        x+=30f
        x+=30f
        g.accept(BarLineDrawer(staff, x, BarLineDrawer.repeatStart()))
        x += 30f
        g.accept(BarLineDrawer(staff, x, BarLineDrawer.repeatEnd()))
        x += 30f
        g.accept(BarLineDrawer(staff, x, BarLineDrawer.simple()))
        x += 30f
        g.accept(BarLineDrawer(staff, x, BarLineDrawer.double()))
        x += 30f
        g.accept(BarLineDrawer(staff, x, BarLineDrawer.pieceEnd()))
        x += 30f
        g.accept(BarLineDrawer(staff, x, listOf(BarLineDrawer.BarLineStyle.RepeatDots, BarLineDrawer.BarLineStyle.RepeatDots)))
        x += 30f
        g.accept(BarLineDrawer(staff, x, listOf(BarLineDrawer.BarLineStyle.RepeatDots, BarLineDrawer.BarLineStyle.Thin, BarLineDrawer.BarLineStyle.RepeatDots)))
        x += 30f
        val element = svg.getSVGElement("music")
        System.out.println(Some.prettyPrint(element))

        val filePath = Files.createTempFile("mus", ".svg")
        val writer = FileWriter(filePath.toFile())
        System.out.println(filePath)
        writer.write(element)
        writer.close()
    }

}
fun main() {
    val view = ContinousViewSheetMusic(900, 400)
    view.draw()
}
