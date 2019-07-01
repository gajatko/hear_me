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
        val staff = g.drawStaff(10f, 100f, 800f, 4)
        var x = 0f
        for (i in 0..25) {
            x += if (i % 2 == 0)
                30f
            else
                15f
            val n = i - 5
            g.drawNote(staff, x, n)
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
