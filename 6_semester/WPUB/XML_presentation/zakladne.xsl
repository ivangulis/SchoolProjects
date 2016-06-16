<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">

<!-- Vyfarbí bunku v stĺpci -->
<xsl:template name="vybavStlpec">
	<xsl:if test="@farba">
		<xsl:call-template name="zafarby" >
			<xsl:with-param name="farba" select="@farba"> </xsl:with-param>
		</xsl:call-template>
	</xsl:if>
	<xsl:if test="not(@farba)">
		<xsl:value-of select="."/>
	</xsl:if>
</xsl:template>

<!-- Vypočíta číslo strany -->
<xsl:template name="vybavCisla">
	<xsl:variable name="strana" select="count(preceding::slide)+1" />
	<xsl:value-of select="$strana"/>
</xsl:template>

<!-- Vypočíta číslo a menovku obrázka -->
<xsl:template name="vybavCisloObrazka">
	<xsl:variable name="cislo" select="count(preceding::obrazok)+1" />
	Obrázok <xsl:value-of select="$cislo" />
</xsl:template>

<!-- Vypočíta číslo a menovku tabuľky -->
<xsl:template name="vybavCisloTabulky">
	<xsl:variable name="cislo" select="count(preceding::table)+1" />
	Tabuľka <xsl:value-of select="$cislo" />
</xsl:template>


</xsl:stylesheet>
