<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:param name="elementname"/>
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
    <xsl:template match="/">
        <xsl:text>Total # of Elements "</xsl:text>
        <xsl:value-of select="$elementname"/>
        <xsl:text>": </xsl:text><xsl:value-of select="count(//*[local-name() = $elementname])"/>
    </xsl:template>

</xsl:stylesheet>
