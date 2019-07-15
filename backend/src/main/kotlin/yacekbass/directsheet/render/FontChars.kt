package yacekbass.directsheet.render

enum class FontChars(val charCode: kotlin.Int, val sizeFix: Float? = null) {
    WHOLE_NOTE(0xE134)
    , QUARTER_NOTE(0xE136)
    , C_CLEF(0xE1A5)
    , G_CLEF(0xE1AA, 4.8f)
    , M(0x006D)
}