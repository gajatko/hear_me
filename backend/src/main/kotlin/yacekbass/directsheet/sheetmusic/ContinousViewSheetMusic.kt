package yacekbass.directsheet.sheetmusic;

import org.jfree.graphics2d.svg.SVGGraphics2D
import yacekbass.directsheet.render.SheetMusic2DImpl
import java.io.FileWriter
import java.nio.file.Files

class ContinousViewSheetMusic(w: Int, h: Int) {

    val svg = SVGGraphics2D(w, h)
    val g = SheetMusic2DImpl(svg)

    fun draw()
    {
        val staff = g.drawStaff(10f, 10f, 180f, 5)
        g.drawNote(staff, 0f, 0)


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
    val view = ContinousViewSheetMusic(200, 100)
    view.draw()
}
