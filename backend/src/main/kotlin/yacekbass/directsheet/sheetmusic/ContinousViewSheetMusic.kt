package yacekbass.directsheet.sheetmusic;

import org.jfree.graphics2d.svg.SVGGraphics2D
import yacekbass.directsheet.render.FontChars
import yacekbass.directsheet.render.SheetMusic2DImpl
import java.io.FileWriter
import java.nio.file.Files

class ContinousViewSheetMusic(w: Int, h: Int) {

    val svg = SVGGraphics2D(w, h)
    val g = SheetMusic2DImpl(svg)

    fun draw()
    {
        val staff = g.drawStaff(10f, 100f, 800f, 5)
        var x = 0f
        g.currentSymbol = FontChars.G_CLEF
        g.drawSymbol(staff, x, 6)
        x+=30f
        g.currentSymbol = FontChars.C_CLEF
        g.drawSymbol(staff, x, 4)
        x+=30f
        for (i in 0..25) {
            if (i % 2 == 0) {
                g.currentSymbol = FontChars.WHOLE_NOTE
                x += 30f
            }
            else {
                g.currentSymbol = FontChars.QUARTER_NOTE
                x += 15f
            }
            val n = i - 5
            g.drawSymbol(staff, x, n)
        }


        val element = svg.getSVGElement("music")
        System.out.println(Some.prettyPrint(element))

        val filePath = Files.createTempFile("mus", ".svg")
        val writer = FileWriter(filePath.toFile())
        System.out.println(filePath)
        writer.write(element)
        writer.close()
    }

}
fun main(args: Array<String>) {
    val view = ContinousViewSheetMusic(900, 400)
    view.draw()
}
