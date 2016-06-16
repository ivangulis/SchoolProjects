<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:exsl="http://exslt.org/common" version="2.0" exclude-result-prefixes="exsl">
<xsl:include href="zakladne.xsl"/>
<xsl:output method="html" indent="yes" name="html"/>

<!-- Úložisko pre HTML slidy -->
<xsl:param name="cesta">/skola6/WP/zadanie3/</xsl:param>

<xsl:template match="/">
    <xsl:for-each select="prezentacia/slide">
		<xsl:variable name="strana" select="count(preceding-sibling::slide)+1" />
		<xsl:variable name="filename" select="concat($cesta, $strana,'.html')" />
		<!-- Ukladá všetko čo je nižšie -->
	<xsl:result-document href="{$filename}" format="html">
	<html>
		<body>		
		
		<xsl:call-template name="nextBack"/>
		
		<div style="width:100%; height:80%;">
			<xsl:if test="@typ!='titulny'">
				<xsl:call-template name="tituly"/>
			</xsl:if>
			<xsl:apply-templates select="."/>
		</div>

		<xsl:call-template name="cisla"/>
		
		</body>
	</html>
	</xsl:result-document>
    </xsl:for-each>
</xsl:template>

<!-- Template pre titulny slide -->
<xsl:template match="slide[@typ='titulny']">
	<div>
	<h1 style="text-align:center; font-size:500%; margin-top:20%; margin-right:5%;" ><xsl:value-of select="title"/></h1>
	<h3 style="text-align:center; font-size:300%;" ><xsl:value-of select="paragraf/text"/></h3>
	</div>
</xsl:template>

<!-- Template pre slide s textom aj obrázkom -->
<xsl:template match="slide[@typ='textovoObrazkovy']">
	<div>
	<div style="float:left; width:50%;">
	<ul>
		<xsl:for-each select="paragraf/list/listitem">
			<li style="font-size:300%; margin-left:10%; margin-bottom:12px;"><xsl:apply-templates/></li>
		</xsl:for-each>
	</ul>
	</div>
	<div>
	<img style="max-height:100%; max-width:100%; display:block;" src="{obrazok/@link}"/>
	<p style="font-size:200%; text-align:right; margin-right:25%;"><xsl:call-template name="vybavCisloObrazka"/></p>
	</div>
	</div>
</xsl:template>

<!-- Template pre slide, kde je iba tabuľka -->
<xsl:template match="slide[@typ='tabulkovy']">
	<table style="margin-right:auto; margin-left:auto;" border="1">
		<tr bgcolor="#9acd32">
			<xsl:for-each select="table/hlavicka/stlpec">
				<th style="font-size:200%; margin-bottom:12px;"><xsl:value-of select="."/></th>
			</xsl:for-each>
		</tr>
		<xsl:for-each select="table/tablerow">
		<tr>
			<xsl:for-each select="stlpec">
				<xsl:if test="position() = 1">
				<td style="text-align:left; font-size:200%; margin-bottom:12px;"><xsl:value-of select="."/></td>
				</xsl:if>
				<xsl:if test="position() != 1">
				<td style="text-align:center; font-size:200%; margin-bottom:12px;">
					<xsl:call-template name="vybavStlpec"></xsl:call-template>
				</td>
				</xsl:if>
			</xsl:for-each>
		</tr>
		</xsl:for-each>
	</table>
	<p style="font-size:200%; text-align:center;"><xsl:call-template name="vybavCisloTabulky"/></p>
</xsl:template>

<!-- Template pre slide, kde je iba obrázok -->
<xsl:template match="slide[@typ='obrazkovy']">
	<div>
	<img style="max-height:100%; max-width:100%; display:block; margin-left:auto; margin-right:auto;" src="{obrazok/@link}"/>
	<p style="font-size:200%; text-align:center;"><xsl:call-template name="vybavCisloObrazka"/></p>
	</div>
</xsl:template>

<!-- Template pre slide, kde je obsah a obrázok -->
<xsl:template match="slide[@typ='obsahovy']">
	<div>
	<div style="display:block; height:60%; float:left; width:55%;">
	<ul>
	<xsl:for-each select="//prezentacia/slide[not(@typ='obsahovy' or @typ='titulny' or @typ='zdrojovy')]">
		<li style="font-size:300%; margin-left:10%; margin-bottom:6px;"><xsl:value-of select="title"/></li>
	</xsl:for-each>
	</ul>
	</div>
	<div style="height:60%; float:right; width:45%;">
	<img style="max-height:100%; max-width:100%; display:block;" src="{obrazok/@link}"/>
	<p style="font-size:200%; text-align:center;"><xsl:call-template name="vybavCisloObrazka"/></p>
	</div>
	</div>
</xsl:template>

<!-- Template pre slide, kde sú zdroje obrázkov -->
<xsl:template match="slide[@typ='zdrojovy']" >
	<div>
	<ul>
	<xsl:for-each select="//obrazok">
		<xsl:variable name="cislo" select="count(preceding::obrazok)+1" />
		<xsl:if test="@zdroj">
			<li style="font-size:200%; margin-left:5%; margin-bottom:12px;"><xsl:copy-of select="$cislo" />. <a href="{@zdroj}"><xsl:value-of select="@zdroj"/></a></li>
		</xsl:if>
		<xsl:if test="not(@zdroj)">
			<li style="font-size:200%; margin-left:5%; margin-bottom:12px;"><xsl:copy-of select="$cislo" />. Vlastný obrázok</li>
		</xsl:if>
	</xsl:for-each>
	</ul>
	</div>
</xsl:template>

<!-- Template pre slide, kde sú iba odrážky -->
<xsl:template match="slide[@typ='odrazkovy']" >
	<div style="float:left; width:100%;">
	<ul>
		<xsl:for-each select="paragraf/list/listitem">
			<li style="font-size:300%; margin-left:10%; margin-bottom:12px;"><xsl:apply-templates/></li>
		</xsl:for-each>
	</ul>
	</div>
</xsl:template>

<!-- Template pre slide, kde je iba text -->
<xsl:template match="slide[@typ='textovy']" >
	<div style="float:left; width:100%;">
		<p style="font-size:300%; margin-left:10%; margin-bottom:12px;"><xsl:apply-templates/></p>
	</div>
</xsl:template>

<!-- Template pre vygenerovanie NEXT a BACK pre pohyb v prezentácii -->
<xsl:template name="nextBack">
		<xsl:variable name="strana" select="count(preceding::slide)+1" />
		<div style="float:right; width:10%;">
		<xsl:if test="$strana &lt; count(//slide)">
			<div style="text-align:right; margin-right:5%; font-size:250%;">
				<a href="{$strana + 1}.html">Next</a>
			</div>
		</xsl:if>
		<xsl:if test="$strana &gt; 1">
			<div style="text-align:right; margin-right:5%; font-size:250%;">
				<a href="{$strana - 1}.html">Back</a>
			</div>
		</xsl:if>
		</div>
</xsl:template>

<!-- Template pre generovanie čísel strán -->
<xsl:template name="cisla">
		<div style="float:right; width:10%;">
			<xsl:if test="@typ!='titulny'">
				<p style="text-align:right; margin-right:150px; font-size:300%;"><xsl:call-template name="vybavCisla"></xsl:call-template></p>
			</xsl:if>
		</div>
</xsl:template>

<!-- Template pre nadpisy -->
<xsl:template name="tituly">
	<h1 style="font-size:500%; margin-left:10%; margin-top:5%;"><xsl:value-of select="title"/></h1>
</xsl:template>

<!-- Hrubý text -->
<xsl:template match="bold">
        <span style="font-weight:bold;">
            <xsl:apply-templates/>
        </span>  
</xsl:template>

<!-- Krivý text -->
<xsl:template match="kurziva">
        <span style="font-style:italic;">
            <xsl:apply-templates />
        </span>  
</xsl:template>

<!-- Zafarbený text -->
<xsl:template name="zafarby">
	<xsl:param name="farba"/>
        <span style="color:{$farba};">
            <xsl:apply-templates />
        </span>  
</xsl:template>

</xsl:stylesheet>