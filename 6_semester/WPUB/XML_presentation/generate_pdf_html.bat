echo Generating HTML

call java -jar C:\saxon\saxon9he.jar -s:prezentacia.xml -xsl:dohtml.xsl

call java -jar C:\saxon\saxon9he.jar -o:prezentacia.fo -s:prezentacia.xml -xsl:dopdf.xsl

echo Generating PDF

call C:\docbook\xep\xep.bat -fo prezentacia.fo

PAUSE