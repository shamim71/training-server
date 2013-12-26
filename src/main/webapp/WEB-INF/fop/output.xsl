<?xml version="1.0"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" />
	<xsl:template match="example">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="page">
					<!-- background-image="watermark.png" -->
					<fo:region-body region-name="body" margin-top="0.5in"
						margin-bottom="1in" margin-left="2in" margin-right="2in"
						background-repeat="no-repeat" background-position-horizontal="left"
						background-position-vertical="top" />
					/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="page">
				<fo:flow flow-name="body">
					<fo:block>
						<fo:external-graphic src="url('WEB-INF/fop/logoVersacom.jpg')" content-width="scale-to-fit" width="100%"/>
					</fo:block>
					<fo:block font-size="14pt" text-align="center" margin-top="1cm" font-family="sans-serif" >
						Certificate of Completion
					</fo:block>
					<fo:block font-size="14pt" text-align="center" margin-top="1cm" font-family="sans-serif" >
						is hereby granted to
					</fo:block>					
					<fo:block font-size="20pt" text-align="center" margin-top="2cm" font-family="sans-serif">
						<xsl:value-of select="name" />
					</fo:block>
					<fo:block font-size="14pt" text-align="center" margin-top="1cm" font-family="sans-serif" >
						to certify that he/she has completed to satisfaction
					</fo:block>	
					<fo:block font-size="18pt" text-align="center" margin-top="1cm" font-family="sans-serif">
						<xsl:value-of select="course" />
					</fo:block>
					<fo:block font-size="14pt" text-align="center" margin-top="1cm" font-family="sans-serif" >
						Granted: <xsl:value-of select="date" />
					</fo:block>																
					<fo:block margin-top="2cm" text-align="center">
						<xsl:apply-templates select="serial" />
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="serial">
		<xsl:variable name="message" select="." />
		<fo:instream-foreign-object>
			<barcode:barcode xmlns:barcode="http://barcode4j.krysalis.org/ns"
				message="{$message}">
				<barcode:code128>
					<module-width>0.75mm</module-width>
					<barcode:height>30mm</barcode:height>
					<human-readable>
						<placement>bottom</placement>
						<font-name>Helvetica</font-name>
						<font-size>14pt</font-size>
					</human-readable>
				</barcode:code128>
			</barcode:barcode>
		</fo:instream-foreign-object>
	</xsl:template>
</xsl:stylesheet>
