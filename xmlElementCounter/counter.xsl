<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes" />
    <xsl:template match="/">
        <xsl:text>Total # of Elements "ELEMENT_NAME": </xsl:text><xsl:value-of select="count(//ELEMENT_NAME)"/>
    </xsl:template>

</xsl:stylesheet>
