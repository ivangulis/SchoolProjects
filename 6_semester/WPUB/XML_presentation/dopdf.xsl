<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:include href="zakladne.xsl"/>

<xsl:template match="/">
<fo:root>
<fo:layout-master-set>	
	<fo:simple-page-master master-name="prezentacia" page-width="297mm" page-height="210mm" margin-top="1cm" margin-bottom="0cm" margin-left="0cm"  margin-right="1cm">
		<fo:region-body   margin="3cm"/>
		<fo:region-before extent="2cm"/>
		<fo:region-after  extent="2cm"/>
		<fo:region-start  extent="2cm"/>
		<fo:region-end    extent="2cm"/>
	</fo:simple-page-master>
	
</fo:layout-master-set>


<fo:page-sequence master-reference="prezentacia">
<!-- Iba pre čísla strán -->
	<fo:static-content flow-name="xsl-region-after">
				<fo:block font-size="22pt" text-align="right">
					<fo:page-number/>
				</fo:block>
	</fo:static-content>

	<!-- Celý obsah -->
  <fo:flow flow-name="xsl-region-body">
	<xsl:for-each select="prezentacia/slide">
		<fo:block break-after="page">
			<xsl:if test="@typ!='titulny'">
				<xsl:call-template name="tituly"/>
			</xsl:if>
			<xsl:apply-templates select="." />
		</fo:block>
    </xsl:for-each>

  </fo:flow>	
</fo:page-sequence>
</fo:root>
</xsl:template>

<!-- Template pre titulny slide -->
<xsl:template match="slide[@typ='titulny']">
	<fo:block font-family="arial" text-align="center" font-size="40pt" font-weight="bold" margin-top="5cm" space-after="6pt">
		<xsl:value-of select="title"/>
	</fo:block>
	
	<fo:block text-align="center" font-size="32pt" font-family="arial">
		<xsl:value-of select="paragraf/text"/>
	</fo:block>
</xsl:template>

<!-- Template pre slide, kde je iba text -->
<xsl:template match="slide[@typ='textovy']" >
	<fo:block text-indent="5mm" font-family="arial" font-size="18pt">
		<xsl:apply-templates/>
	</fo:block>
</xsl:template>

<!-- Template pre slide s textom aj obrázkom -->
<xsl:template match="slide[@typ='textovoObrazkovy']">
<fo:block intrusion-displace="block">
	<fo:block-container width="40%">
	<fo:block>
	<fo:list-block>
	<xsl:for-each select="paragraf/list/listitem">
	<fo:list-item>
		<fo:list-item-label end-indent="0">
			<fo:block>- </fo:block>
		</fo:list-item-label>
		<fo:list-item-body start-indent="0">
			<fo:block text-indent="5mm" font-family="arial" font-size="18pt" space-before="3mm" space-after="3mm">
				<xsl:apply-templates/>
			</fo:block>
		</fo:list-item-body>
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
	</fo:block>
	</fo:block-container>

	<fo:block-container top="2cm" left="2cm" absolute-position="absolute">
	<fo:block space-before="12pt" space-after="1cm" text-align="right">
      <fo:external-graphic src="url({obrazok/@link})" width="12cm" content-width="scale-to-fit" scaling="uniform" />
    </fo:block>
	<fo:block font-size="16pt" text-align="right" margin-right="25%">
		<xsl:call-template name="vybavCisloObrazka"/>
	</fo:block>
	</fo:block-container>
</fo:block>
</xsl:template>

<!-- Template pre slide, kde je iba tabuľka -->
<xsl:template match="slide[@typ='tabulkovy']">
<fo:block-container right="auto" left="auto">
    <fo:block margin-left="5%" font-size="16pt" space-after="1cm">
        <fo:table border="solid" border-collapse="collapse">
            <fo:table-header>
                <fo:table-row space-after="10px">
					<xsl:for-each select="table/hlavicka/stlpec">
						<fo:table-cell background-color="#9acd32" vertical-align="middle" border="1pt solid black" display-align="after">
							<fo:block text-align="center" font-weight="bold"><xsl:value-of select="self::stlpec"/></fo:block>
						</fo:table-cell>
					</xsl:for-each>
                </fo:table-row>
            </fo:table-header>
            <fo:table-body>
			<xsl:for-each select="table/tablerow">
				<fo:table-row>
				<xsl:for-each select="stlpec">
					<xsl:if test="position() = 1">
						<fo:table-cell vertical-align="middle" border="1pt solid black" display-align="after">
							<fo:block text-align="left" ><xsl:value-of select="self::stlpec"/></fo:block>
						</fo:table-cell>
					</xsl:if>
					<xsl:if test="position() != 1">
						<fo:table-cell vertical-align="middle" border="1pt solid black" display-align="after">
								<fo:block text-align="center">
									<xsl:call-template name="vybavStlpec"></xsl:call-template>
								</fo:block>
						</fo:table-cell>
					</xsl:if>
				</xsl:for-each>
				</fo:table-row>
			</xsl:for-each>
            </fo:table-body>
        </fo:table>
    </fo:block>
	<fo:block font-size="16pt" text-align="center">
		<xsl:call-template name="vybavCisloTabulky"/>
	</fo:block>
</fo:block-container>
</xsl:template>

<!-- Template pre slide, kde je iba obrázok -->
<xsl:template match="slide[@typ='obrazkovy']">
<fo:block-container top="50pt" absolute-position="absolute">
	<fo:block space-before="12pt" text-align="center" space-after="1cm">
      <fo:external-graphic src="url({obrazok/@link})" width="18cm" content-width="scale-to-fit" scaling="uniform" />
    </fo:block>
	<fo:block font-size="16pt" text-align="center">
		<xsl:call-template name="vybavCisloObrazka"/>
	</fo:block>
</fo:block-container>
</xsl:template>

<!-- Template pre slide, kde je obsah a obrázok -->
<xsl:template match="slide[@typ='obsahovy']">
<fo:block intrusion-displace="block">
	<fo:block-container width="45%">
	<fo:block>
	<fo:list-block>
	<xsl:for-each select="//prezentacia/slide[not(@typ='obsahovy' or @typ='titulny' or @typ='zdrojovy')]">
	<fo:list-item>
		<fo:list-item-label end-indent="0">
			<fo:block>- </fo:block>
		</fo:list-item-label>
		<fo:list-item-body start-indent="0">
			<fo:block text-indent="5mm" font-family="arial" font-size="18pt" space-before="3mm" space-after="3mm">
				<xsl:value-of select="title"/>
			</fo:block>
		</fo:list-item-body>
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
	</fo:block>
	</fo:block-container>

	<fo:block-container top="2cm" left="2cm" absolute-position="absolute">
	<fo:block padding-left="20mm" space-before="12pt" space-after="1cm" text-align="right">
      <fo:external-graphic src="url({obrazok/@link})" width="12cm" content-width="scale-to-fit" scaling="uniform" />
    </fo:block>
	<fo:block font-size="16pt" text-align="right" margin-right="25%">
		<xsl:call-template name="vybavCisloObrazka"/>
	</fo:block>
	</fo:block-container>
</fo:block>

</xsl:template>

<!-- Template pre slide, kde sú zdroje obrázkov -->
<xsl:template match="slide[@typ='zdrojovy']" >
	<fo:block>
	<fo:list-block>
	<xsl:for-each select="//obrazok">
	<xsl:variable name="cislo" select="count(preceding::obrazok)+1" />
	<fo:list-item>
		<fo:list-item-label end-indent="0">
			<fo:block>- </fo:block>
		</fo:list-item-label>
		<fo:list-item-body  start-indent="0" >
			<fo:block text-indent="5mm" font-family="arial" font-size="14pt" space-before="3mm" space-after="3mm">
				<xsl:if test="@zdroj">
					<xsl:copy-of select="$cislo" />. <fo:basic-link external-destination="url({@zdroj})" color="blue" text-decoration="underline"><xsl:value-of select="@zdroj"/></fo:basic-link>
				</xsl:if>
				<xsl:if test="not(@zdroj)">
					<xsl:copy-of select="$cislo" />. Vlastný obrázok
				</xsl:if>
			</fo:block>
		</fo:list-item-body>
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
	</fo:block>
</xsl:template>

<!-- Template pre slide, kde sú iba odrážky -->
<xsl:template match="slide[@typ='odrazkovy']" >
	<fo:block>
	<fo:list-block>
	<xsl:for-each select="paragraf/list/listitem">
	<fo:list-item>
		<fo:list-item-label end-indent="0">
			<fo:block>- </fo:block>
		</fo:list-item-label>
		<fo:list-item-body start-indent="0">
			<fo:block text-indent="5mm" font-family="arial" font-size="18pt" space-before="3mm" space-after="3mm">
				<xsl:apply-templates/>
			</fo:block>
		</fo:list-item-body>
	</fo:list-item>
	</xsl:for-each>
	</fo:list-block>
	</fo:block>
</xsl:template>

<!-- Template pre nadpisy -->
<xsl:template name="tituly">
	<fo:block font-family="arial" text-align="left" font-size="36pt" font-weight="bold" space-before="0" margin-bottom="0.5cm">
		<xsl:value-of select="title"/>
	</fo:block>
</xsl:template>

<!-- Hrubý text -->
<xsl:template match="bold">
        <fo:inline font-weight="bold">
            <xsl:apply-templates/>
        </fo:inline>  
</xsl:template>

<!-- Krivý text -->
<xsl:template match="kurziva">
        <fo:inline font-style="italic">
            <xsl:apply-templates />
        </fo:inline>
</xsl:template>

<!-- Zafarbený text -->
<xsl:template name="zafarby">
	<xsl:param name="farba"/>
        <fo:inline  color="{$farba}">
            <xsl:apply-templates />
        </fo:inline>  
</xsl:template>

</xsl:stylesheet>