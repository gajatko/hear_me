package yacekbass.directsheet.render

import yacekbass.directsheet.render.FontCharFlags.REQUIRES_LEDGER_LINES

enum class FontChars(val charCode: kotlin.Int, val flags: Array<FontCharFlags> = arrayOf(), val sizeFix : Float? = null) {
    WHOLE_NOTE(0xE134, arrayOf(REQUIRES_LEDGER_LINES))
    , QUARTER_NOTE(0xE136, arrayOf(REQUIRES_LEDGER_LINES))
    , C_CLEF(0xE1A5)
    , G_CLEF(0xE1AA, arrayOf(), 1.1f)
    , M(0x006D)
}

enum class FontCharFlags {
    REQUIRES_LEDGER_LINES,
}