<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:cfdi="http://www.sat.gob.mx/cfd/4" xmlns:cce11="http://www.sat.gob.mx/ComercioExterior11" xmlns:donat="http://www.sat.gob.mx/donat" xmlns:divisas="http://www.sat.gob.mx/divisas" xmlns:implocal="http://www.sat.gob.mx/implocal" xmlns:leyendasFisc="http://www.sat.gob.mx/leyendasFiscales" xmlns:pfic="http://www.sat.gob.mx/pfic" xmlns:tpe="http://www.sat.gob.mx/TuristaPasajeroExtranjero" xmlns:nomina12="http://www.sat.gob.mx/nomina12" xmlns:registrofiscal="http://www.sat.gob.mx/registrofiscal" xmlns:pagoenespecie="http://www.sat.gob.mx/pagoenespecie" xmlns:aerolineas="http://www.sat.gob.mx/aerolineas" xmlns:valesdedespensa="http://www.sat.gob.mx/valesdedespensa" xmlns:notariospublicos="http://www.sat.gob.mx/notariospublicos" xmlns:vehiculousado="http://www.sat.gob.mx/vehiculousado" xmlns:servicioparcial="http://www.sat.gob.mx/servicioparcialconstruccion" xmlns:decreto="http://www.sat.gob.mx/renovacionysustitucionvehiculos" xmlns:destruccion="http://www.sat.gob.mx/certificadodestruccion" xmlns:obrasarte="http://www.sat.gob.mx/arteantiguedades" xmlns:ine="http://www.sat.gob.mx/ine" xmlns:iedu="http://www.sat.gob.mx/iedu" xmlns:ventavehiculos="http://www.sat.gob.mx/ventavehiculos" xmlns:detallista="http://www.sat.gob.mx/detallista" xmlns:ecc12="http://www.sat.gob.mx/EstadoDeCuentaCombustible12" xmlns:consumodecombustibles11="http://www.sat.gob.mx/ConsumoDeCombustibles11" xmlns:gceh="http://www.sat.gob.mx/GastosHidrocarburos10" xmlns:ieeh="http://www.sat.gob.mx/IngresosHidrocarburos10" xmlns:cartaporte20="http://www.sat.gob.mx/CartaPorte20" xmlns:pago20="http://www.sat.gob.mx/Pagos20">

  <!-- Con el siguiente método se establece que la salida deberá ser en texto -->
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="no"/>
  <!--
		En esta sección se define la inclusión de las plantillas de utilerías para colapsar espacios
	-->
	<!-- Manejador de datos requeridos -->
	<xsl:template name="Requerido">
		<xsl:param name="valor"/>|<xsl:call-template name="ManejaEspacios">
			<xsl:with-param name="s" select="$valor"/>
		</xsl:call-template>
	</xsl:template>

	<!-- Manejador de datos opcionales -->
	<xsl:template name="Opcional">
		<xsl:param name="valor"/>
		<xsl:if test="$valor">|<xsl:call-template name="ManejaEspacios"><xsl:with-param name="s" select="$valor"/></xsl:call-template></xsl:if>
	</xsl:template>
	
	<!-- Normalizador de espacios en blanco -->
	<xsl:template name="ManejaEspacios">
		<xsl:param name="s"/>
		<xsl:value-of select="normalize-space(string($s))"/>
	</xsl:template>

  <!-- 
		En esta sección se define la inclusión de las demás plantillas de transformación para 
		la generación de las cadenas originales de los complementos fiscales 
	-->
	
  <xsl:include href="ecc.xslt"/>
  <xsl:include href="donat11.xslt"/>
  <xsl:include href="divisas.xslt"/>
  <xsl:include href="implocal.xslt"/>
  <xsl:include href="terceros11.xslt"/>
  <xsl:include href="iedu.xslt"/>
  <xsl:include href="ventavehiculos11.xslt"/>
  <xsl:include href="pfic.xslt"/>
  <xsl:include href="TuristaPasajeroExtranjero.xslt"/>
  <xsl:include href="leyendasFisc.xslt"/>
  <xsl:include href="nomina11.xslt"/>
  <xsl:include href="cfdiregistrofiscal.xslt"/>
  <xsl:include href="pagoenespecie.xslt"/>
  <xsl:include href="consumodecombustibles.xslt"/>
  <xsl:include href="valesdedespensa.xslt"/>
  <xsl:include href="aerolineas.xslt"/>
  <xsl:include href="notariospublicos.xslt"/>
  <xsl:include href="vehiculousado.xslt"/>
  <xsl:include href="servicioparcialconstruccion.xslt"/>
  <xsl:include href="certificadodedestruccion.xslt"/>
  <xsl:include href="renovacionysustitucionvehiculos.xslt"/>
  <xsl:include href="obrasarteantiguedades.xslt"/>
  <xsl:include href="AcreditamientoIEPS10.xslt"/>
  <xsl:include href="ecc11.xslt"/>
  <xsl:include href="ine11.xslt"/>
  <xsl:include href="nomina12.xslt"/>
  <xsl:include href="Pagos10.xslt"/>
  <xsl:include href="ComercioExterior11.xslt"/>
  <xsl:include href="detallista.xslt"/>
  <xsl:include href="ecc12.xslt"/>
  <xsl:include href="consumodeCombustibles11.xslt"/>
  <xsl:include href="cartaPorte.xslt"/>


	<!-- Manejador de nodos tipo donat:Donatarias -->
	<xsl:template match="donat:Donatarias">
		<!-- Iniciamos el tratamiento de los atributos de donat:Donatarias -->
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@version"/></xsl:call-template>
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@noAutorizacion"/></xsl:call-template>
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@fechaAutorizacion"/></xsl:call-template>
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@leyenda"/></xsl:call-template>
	</xsl:template>
	
	<!-- Manejador de nodos tipo divisas:Divisas -->
	<xsl:template match="divisas:Divisas">
		<!-- Iniciamos el tratamiento de los atributos de divisas:Divisas -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@tipoOperacion"/>
		</xsl:call-template>
	</xsl:template>

	<!-- Manejador de nodos tipo implocal -->
	<xsl:template match="implocal:ImpuestosLocales">
		<!--Iniciamos el tratamiento de los atributos de ImpuestosLocales -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TotaldeRetenciones"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TotaldeTraslados"/>
		</xsl:call-template>
		<xsl:for-each select="implocal:RetencionesLocales">
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@ImpLocRetenido"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@TasadeRetencion"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Importe"/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:for-each select="implocal:TrasladosLocales">
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@ImpLocTrasladado"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@TasadeTraslado"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Importe"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

	<!-- Manejador de nodos tipo leyendasFiscales -->
	<xsl:template match="leyendasFisc:LeyendasFiscales">
		<!--Iniciamos el tratamiento de los atributos del complemento LeyendasFiscales -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<!-- Manejo de los atributos de las leyendas Fiscales-->
		<xsl:for-each select="./leyendasFisc:Leyenda">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!-- Manejador de nodos tipo Información de las leyendas -->
	<xsl:template match="leyendasFisc:Leyenda">
		<!-- Manejo de los atributos de la leyenda -->
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@disposicionFiscal"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@norma"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@textoLeyenda"/>
		</xsl:call-template>
	</xsl:template>

	<!-- Manejador de nodos tipo pfic:PFintegranteCoordinado -->
	<xsl:template match="pfic:PFintegranteCoordinado">
		<!-- Iniciamos el tratamiento de los atributos de pfic:PFintegranteCoordinado -->
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@version"/></xsl:call-template>
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@ClaveVehicular"/></xsl:call-template>
		<xsl:call-template name="Requerido"><xsl:with-param name="valor" select="./@Placa"/></xsl:call-template>
		<xsl:call-template name="Opcional"><xsl:with-param name="valor" select="./@RFCPF"/></xsl:call-template>
	</xsl:template>
	
	<!-- Manejador de nodos tipo tpe:TuristaPasajeroExtranjero -->
	<xsl:template match="tpe:TuristaPasajeroExtranjero">
		<!--Iniciamos el tratamiento de los atributos de tpe:TuristaPasajeroExtranjero-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@fechadeTransito"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@tipoTransito"/>
		</xsl:call-template>
		<xsl:apply-templates select="./tpe:datosTransito"/>
	</xsl:template>

	<!-- Manejador de nodos tipo datosTransito-->
	<xsl:template match="tpe:datosTransito">
		<!-- Iniciamos el tratamiento de los atributos de los datos de Transito-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Via"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoId"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumeroId"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Nacionalidad"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@EmpresaTransporte"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@IdTransporte"/>
		</xsl:call-template>
	</xsl:template>

	
  <xsl:template match="nomina12:Nomina">
    <!--Manejador de nodos tipo Nomina-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoNomina" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaPago" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaInicialPago" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaFinalPago" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumDiasPagados" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalPercepciones" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalDeducciones" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalOtrosPagos" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:for-each select="./nomina12:Emisor">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <xsl:for-each select="./nomina12:Receptor">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <xsl:for-each select="./nomina12:Percepciones">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <xsl:for-each select="./nomina12:Deducciones">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <xsl:for-each select="./nomina12:OtrosPagos">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <xsl:for-each select="./nomina12:Incapacidades">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <xsl:template match="nomina12:Emisor">
    <!--Manejador de nodos tipo nomina12:Emisor-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Curp" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RegistroPatronal" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RfcPatronOrigen" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:EntidadSNCF-->
    <xsl:for-each select="./nomina12:EntidadSNCF">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>
  
    <!--  Iniciamos el manejo de los elementos hijo en la secuencia EntidadSNCF-->
  <xsl:template match="nomina12:EntidadSNCF">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@OrigenRecurso" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoRecursoPropio" />
    </xsl:call-template>

  </xsl:template>

  <xsl:template match="nomina12:Receptor">
    <!--Manejador de nodos tipo nomina12:Receptor-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Curp" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumSeguridadSocial" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FechaInicioRelLaboral" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Antigüedad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoContrato" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Sindicalizado" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoJornada" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoRegimen" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumEmpleado" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Departamento" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Puesto" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RiesgoPuesto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PeriodicidadPago" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Banco" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CuentaBancaria" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SalarioBaseCotApor" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SalarioDiarioIntegrado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveEntFed" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:SubContratacion-->
    <xsl:for-each select="./nomina12:SubContratacion">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>
  
    <!--  Iniciamos el manejo de los elementos hijo en la secuencia SubContratacion-->
  <xsl:template match="nomina12:SubContratacion">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RfcLabora" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PorcentajeTiempo" />
    </xsl:call-template>

  </xsl:template>

  <xsl:template match="nomina12:Percepciones">
    <!--Manejador de nodos tipo nomina12:Percepciones-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalSueldos" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalSeparacionIndemnizacion" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalJubilacionPensionRetiro" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TotalGravado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TotalExento" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:Percepcion-->
    <xsl:for-each select="./nomina12:Percepcion">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:JubilacionPensionRetiro-->
    <xsl:for-each select="./nomina12:JubilacionPensionRetiro">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:SeparacionIndemnizacion-->
    <xsl:for-each select="./nomina12:SeparacionIndemnizacion">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>
   <!--  Iniciamos el manejo de los elementos hijo en la secuencia Percepcion-->
  <xsl:template match="nomina12:Percepcion">
    <!--Manejador de nodos tipo nomina12:Percepcion-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoPercepcion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Clave" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Concepto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImporteGravado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImporteExento" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:AccionesOTitulos-->
    <xsl:for-each select="./nomina12:AccionesOTitulos">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <!--  Iniciamos el tratamiento de los atributos de nomina12:HorasExtra-->
    <xsl:for-each select="./nomina12:HorasExtra">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia AccionesOTitulos-->
  <xsl:template match="nomina12:AccionesOTitulos">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ValorMercado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PrecioAlOtorgarse" />
    </xsl:call-template>
  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia HorasExtra-->
  <xsl:template match="nomina12:HorasExtra">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Dias" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoHoras" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@HorasExtra" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImportePagado" />
    </xsl:call-template>
  </xsl:template>
  
   <!--  Iniciamos el manejo de los elementos hijo en la secuencia JubilacionPensionRetiro-->
  <xsl:template match="nomina12:JubilacionPensionRetiro">
    <!--Manejador de nodos tipo nomina12:JubilacionPensionRetiro-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalUnaExhibicion" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalParcialidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoDiario" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IngresoAcumulable" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IngresoNoAcumulable" />
    </xsl:call-template>
  </xsl:template>
  
    <!--  Iniciamos el manejo de los elementos hijo en la secuencia SeparacionIndemnizacion-->
  <xsl:template match="nomina12:SeparacionIndemnizacion">
    <!--Manejador de nodos tipo nomina12:JubilacionPensionRetiro-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TotalPagado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumAñosServicio" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@UltimoSueldoMensOrd" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IngresoAcumulable" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IngresoNoAcumulable" />
    </xsl:call-template>
  </xsl:template>
    
  <xsl:template match="nomina12:Deducciones">
    <!--Manejador de nodos tipo nomina12:Deducciones-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalOtrasDeducciones" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalImpuestosRetenidos" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:Deduccion-->
    <xsl:for-each select="./nomina12:Deduccion">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>
  
    <!--  Iniciamos el manejo de los elementos hijo en la secuencia Deduccion-->
  <xsl:template match="nomina12:Deduccion">
    <!--Manejador de nodos tipo nomina12:Deduccion-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoDeduccion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Clave" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Concepto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe" />
    </xsl:call-template>
  </xsl:template>

    <xsl:template match="nomina12:OtrosPagos">

    <!--  Iniciamos el tratamiento de los atributos de nomina12:OtroPago-->
    <xsl:for-each select="./nomina12:OtroPago">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>
  
  <!--  Iniciamos el manejo de los elementos hijo en la secuencia OtroPago-->
  <xsl:template match="nomina12:OtroPago">
    <!--Manejador de nodos tipo nomina12:OtroPago-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoOtroPago" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Clave" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Concepto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de nomina12:SubsidioAlEmpleo-->
    <xsl:for-each select="./nomina12:SubsidioAlEmpleo">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <!--  Iniciamos el tratamiento de los atributos de nomina12:CompensacionSaldosAFavor-->
    <xsl:for-each select="./nomina12:CompensacionSaldosAFavor">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia SubsidioAlEmpleo-->
  <xsl:template match="nomina12:SubsidioAlEmpleo">
    <!--Manejador de nodos tipo nomina12:SubsidioAlEmpleo-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@SubsidioCausado" />
    </xsl:call-template>
  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia CompensacionSaldosAFavor-->
  <xsl:template match="nomina12:CompensacionSaldosAFavor">
    <!--Manejador de nodos tipo nomina12:CompensacionSaldosAFavor-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@SaldoAFavor" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RemanenteSalFav" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="nomina12:Incapacidades">
    <!--  Iniciamos el tratamiento de los atributos de nomina12:Incapacidades-->
    <xsl:for-each select="./nomina12:Incapacidad">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Incapacidad-->
  <xsl:template match="nomina12:Incapacidad">
    <!--Manejador de nodos tipo nomina12:Incapacidad-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@DiasIncapacidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoIncapacidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ImporteMonetario" />
    </xsl:call-template>

  </xsl:template>

  <!-- Manejador de nodos Registro Fiscal -->
  <xsl:template match="registrofiscal:CFDIRegistroFiscal">

    <!--Iniciamos el tratamiento de los atributos de RegistroFiscal -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Folio"/>
    </xsl:call-template>

  </xsl:template>

  <!-- Manejador de nodos tipo pago en especie-->
  <xsl:template match="pagoenespecie:PagoEnEspecie">

    <!--Iniciamos el tratamiento de los atributos de PagoEnEspecie -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@CvePIC"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FolioSolDon"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PzaArtNombre"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PzaArtTecn"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PzaArtAProd"/>
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PzaArtDim"/>
    </xsl:call-template>

  </xsl:template>
  
  <!-- Manejador de nodos tipo aerolineas:Aerolineas --> 
  <xsl:template match="aerolineas:Aerolineas">
    
    <!-- Iniciamos el tratamiento de los atributos de aerolineas:Aerolineas -->
    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TUA"/>
    </xsl:call-template>
    
    <!-- Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./aerolineas:OtrosCargos"/>
    
  </xsl:template>

  <!-- Manejador de nodos tipo aerolineas:OtrosCargos -->
  <xsl:template match="aerolineas:OtrosCargos">

    <!-- Iniciamos el tratamiento de los atributos de aerolineas:OtrosCargos -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TotalCargos"/>
    </xsl:call-template>
    
    <!-- Iniciamos el manejo de los nodos dependientes -->    
    <xsl:for-each select="./aerolineas:Cargo">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
  </xsl:template>

  <!-- Manejador de nodos tipo aerolineas:Cargo -->
  <xsl:template match="aerolineas:Cargo">
    
    <!-- Iniciamos el tratamiento de los atributos de aerolineas:ConceptoConsumoDeCombustibles -->
    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@CodigoCargo"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe"/>
    </xsl:call-template>
    
  </xsl:template>

  <!-- Manejador de nodos tipo valesdedespensa:ValesDeDespensa --> 
  <xsl:template match="valesdedespensa:ValesDeDespensa">
    
    <!-- Iniciamos el tratamiento de los atributos de valesdedespensa:ValesDeDespensa -->
    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@version"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoOperacion"/>
    </xsl:call-template>
     <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@registroPatronal"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@numeroDeCuenta"/>
    </xsl:call-template>   
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@total"/>
    </xsl:call-template>
    
    <!-- Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./valesdedespensa:Conceptos"/>
    
  </xsl:template>

  <!-- Manejador de nodos tipo valesdedespensa:Conceptos -->
  <xsl:template match="valesdedespensa:Conceptos">
    
    <!-- Iniciamos el manejo de los nodos dependientes -->
    
    <xsl:for-each select="./valesdedespensa:Concepto">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
  </xsl:template>

  <!-- Manejador de nodos tipo valesdedespensa:Concepto -->
  <xsl:template match="valesdedespensa:Concepto">
    
    <!-- Iniciamos el tratamiento de los atributos de valesdedespensa:Concepto -->
    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@identificador"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@fecha"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@rfc"/>
    </xsl:call-template>
      <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@curp"/>
    </xsl:call-template>  
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@nombre"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@numSeguridadSocial"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@importe"/>
    </xsl:call-template>  
       
  </xsl:template>
	
  <!-- Manejador de nodos tipo notariospublicos:NotariosPublicos --> 
  <xsl:template match="notariospublicos:NotariosPublicos">
    
    <!-- Iniciamos el tratamiento de los atributos -->    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>
    
    <!-- Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./notariospublicos:DescInmuebles"/>
    <xsl:apply-templates select="./notariospublicos:DatosOperacion"/>
    <xsl:apply-templates select="./notariospublicos:DatosNotario"/>
    <xsl:apply-templates select="./notariospublicos:DatosEnajenante"/>
    <xsl:apply-templates select="./notariospublicos:DatosAdquiriente"/>
    
  </xsl:template>

    <!-- Manejador de nodos tipo notariospublicos:DescInmuebles -->
    <xsl:template match="notariospublicos:DescInmuebles">
    
      <!-- Iniciamos el manejo de los nodos dependientes -->    
      <xsl:for-each select="./notariospublicos:DescInmueble">
        <xsl:apply-templates select="."/>
      </xsl:for-each>
    
    </xsl:template>

      <!-- Manejador de nodos tipo notariospublicos:DescInmueble -->
      <xsl:template match="notariospublicos:DescInmueble">

        <!-- Iniciamos el tratamiento de los atributos -->
        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@TipoInmueble"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Calle"/>
        </xsl:call-template>
        
        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@NoExterior"/>
        </xsl:call-template>
        
        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@NoInterior"/>
        </xsl:call-template>
        
        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@Colonia"/>
        </xsl:call-template>
        
        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@Localidad"/>
        </xsl:call-template>
        
        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@Referencia"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Municipio"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Estado"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Pais"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@CodigoPostal"/>
        </xsl:call-template>

      </xsl:template>

    <!-- Manejador de nodos tipo notariospublicos:DatosOperacion -->
    <xsl:template match="notariospublicos:DatosOperacion">

      <!-- Iniciamos el tratamiento de los atributos -->
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@NumInstrumentoNotarial"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@FechaInstNotarial"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@MontoOperacion"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Subtotal"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@IVA"/>
      </xsl:call-template>

    </xsl:template>

    <!-- Manejador de nodos tipo notariospublicos:DatosNotario -->
    <xsl:template match="notariospublicos:DatosNotario">

      <!-- Iniciamos el tratamiento de los atributos -->
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@CURP"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@NumNotaria"/>
      </xsl:call-template>

      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@EntidadFederativa"/>
      </xsl:call-template>
      
      <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@Adscripcion"/>
      </xsl:call-template>


    </xsl:template>

    <!-- Manejador de nodos tipo notariospublicos:DatosEnajenante -->
    <xsl:template match="notariospublicos:DatosEnajenante">

      <!-- Iniciamos el tratamiento de los atributos -->
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@CoproSocConyugalE"/>
      </xsl:call-template>
      
      <!-- Iniciamos el manejo de los nodos dependientes -->
      <xsl:if test="./notariospublicos:DatosUnEnajenante">
        <xsl:apply-templates select="./notariospublicos:DatosUnEnajenante"/>
      </xsl:if>
      
      <xsl:if test="./notariospublicos:DatosEnajenantesCopSC">
        <xsl:apply-templates select="./notariospublicos:DatosEnajenantesCopSC"/>
      </xsl:if>

    </xsl:template>
  
      <!-- Manejador de nodos tipo notariospublicos:DatosUnEnajenante -->
      <xsl:template match="notariospublicos:DatosUnEnajenante">

        <!-- Iniciamos el tratamiento de los atributos -->
        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Nombre"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@ApellidoPaterno"/>
        </xsl:call-template>

        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@ApellidoMaterno"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@RFC"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@CURP"/>
        </xsl:call-template>

      </xsl:template>
  
      <!-- Manejador de nodos tipo notariospublicos:DatosEnajenantesCopSC -->
      <xsl:template match="notariospublicos:DatosEnajenantesCopSC">

        <!-- Iniciamos el manejo de los nodos dependientes -->
        <xsl:for-each select="./notariospublicos:DatosEnajenanteCopSC">
          <xsl:apply-templates select="."/>
        </xsl:for-each>

      </xsl:template>

        <!-- Manejador de nodos tipo notariospublicos:DatosEnajenanteCopSC -->
        <xsl:template match="notariospublicos:DatosEnajenanteCopSC">

          <!-- Iniciamos el tratamiento de los atributos -->
          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@Nombre"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@ApellidoPaterno"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@ApellidoMaterno"/>
          </xsl:call-template>

          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@RFC"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@CURP"/>
          </xsl:call-template>

          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@Porcentaje"/>
          </xsl:call-template>

        </xsl:template>

    <!-- Manejador de nodos tipo notariospublicos:DatosAdquiriente -->
    <xsl:template match="notariospublicos:DatosAdquiriente">

      <!-- Iniciamos el tratamiento de los atributos -->
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@CoproSocConyugalE"/>
      </xsl:call-template>

      <!-- Iniciamos el manejo de los nodos dependientes -->
      <xsl:if test="./notariospublicos:DatosUnAdquiriente">
        <xsl:apply-templates select="./notariospublicos:DatosUnAdquiriente"/>
      </xsl:if>

      <xsl:if test="./notariospublicos:DatosAdquirientesCopSC">
        <xsl:apply-templates select="./notariospublicos:DatosAdquirientesCopSC"/>
      </xsl:if>

    </xsl:template>
  
      <!-- Manejador de nodos tipo notariospublicos:DatosUnAdquiriente -->
      <xsl:template match="notariospublicos:DatosUnAdquiriente">

        <!-- Iniciamos el tratamiento de los atributos -->
        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@Nombre"/>
        </xsl:call-template>

        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@ApellidoPaterno"/>
        </xsl:call-template>

        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@ApellidoMaterno"/>
        </xsl:call-template>

        <xsl:call-template name="Requerido">
          <xsl:with-param name="valor" select="./@RFC"/>
        </xsl:call-template>

        <xsl:call-template name="Opcional">
          <xsl:with-param name="valor" select="./@CURP"/>
        </xsl:call-template>

      </xsl:template>
  
      <!-- Manejador de nodos tipo notariospublicos:DatosAdquirientesCopSC -->
      <xsl:template match="notariospublicos:DatosAdquirientesCopSC">

        <!-- Iniciamos el manejo de los nodos dependientes -->
        <xsl:for-each select="./notariospublicos:DatosAdquirienteCopSC">
          <xsl:apply-templates select="."/>
        </xsl:for-each>

      </xsl:template>

        <!-- Manejador de nodos tipo notariospublicos:DatosAdquirienteCopSC -->
        <xsl:template match="notariospublicos:DatosAdquirienteCopSC">

          <!-- Iniciamos el tratamiento de los atributos -->
          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@Nombre"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@ApellidoPaterno"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@ApellidoMaterno"/>
          </xsl:call-template>

          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@RFC"/>
          </xsl:call-template>

          <xsl:call-template name="Opcional">
            <xsl:with-param name="valor" select="./@CURP"/>
          </xsl:call-template>

          <xsl:call-template name="Requerido">
            <xsl:with-param name="valor" select="./@Porcentaje"/>
          </xsl:call-template>

        </xsl:template>
  
    <xsl:template match="vehiculousado:VehiculoUsado">
    <!-- Iniciamos el tratamiento de los atributos de comprobante -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@montoAdquisicion"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@montoEnajenacion"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@claveVehicular"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@marca"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipo"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@modelo"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@numeroMotor"/>
    </xsl:call-template>
        <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@numeroSerie"/>
    </xsl:call-template>
        <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NIV"/>
    </xsl:call-template>
        <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@valor"/>
    </xsl:call-template>
    <!--
			Llamadas para procesar al los sub nodos
		-->
    <xsl:apply-templates select="./vehiculousado:InformacionAduanera"/>
    </xsl:template>
    <!-- Manejador de nodos tipo informacionaduanera -->
    <xsl:template match="vehiculousado:InformacionAduanera">
    <!-- Iniciamos el tratamiento de los atributos del Emisor -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@numero"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@fecha"/>
    </xsl:call-template>
        <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@aduana"/>
    </xsl:call-template>
    </xsl:template>  

  <xsl:template match="servicioparcial:parcialesconstruccion">
    <!--Manejador de nodos tipo parcialesconstruccion-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPerLicoAut" />
    </xsl:call-template>
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./servicioparcial:Inmueble" />
  </xsl:template>
  <xsl:template match="servicioparcial:Inmueble">
    <!--  Iniciamos el tratamiento de los atributos de servicioparcial:Inmueble-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Calle" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NoExterior" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NoInterior" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Colonia" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Localidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Referencia" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Municipio" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Estado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@CodigoPostal" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="decreto:renovacionysustitucionvehiculos">
    <!--Manejador de nodos tipo renovacionysustitucionvehiculos-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoDeDecreto" />
    </xsl:call-template>
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./decreto:DecretoRenovVehicular" />
    <xsl:apply-templates select="./decreto:DecretoSustitVehicular" />
  </xsl:template>
  
  <xsl:template match="decreto:DecretoRenovVehicular">
    <!--  Iniciamos el tratamiento de los atributos de sustitucionvehiculos:DecretoRenovVehicular-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@VehEnaj" />
    </xsl:call-template>
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:for-each select="./decreto:VehiculosUsadosEnajenadoPermAlFab">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:apply-templates select="./decreto:VehiculoNuvoSemEnajenadoFabAlPerm" />
  </xsl:template>

  <xsl:template match="decreto:DecretoSustitVehicular">
    <!--  Iniciamos el tratamiento de los atributos de sustitucionvehiculos:DecretoSustitVehicular-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@VehEnaj" />
    </xsl:call-template>
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./decreto:VehiculoUsadoEnajenadoPermAlFab" />
    <xsl:apply-templates select="./decreto:VehiculoNuvoSemEnajenadoFabAlPerm" />
  </xsl:template>

  <xsl:template match="decreto:VehiculosUsadosEnajenadoPermAlFab">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PrecioVehUsado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoVeh" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Marca" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipooClase" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Modelo" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NIV" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumSerie" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPlacas" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumMotor" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumFolTarjCir" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumPedIm" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Aduana" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FechaRegulVeh" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Foliofiscal" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="decreto:VehiculoNuvoSemEnajenadoFabAlPerm">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Modelo" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPlacas" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RFC" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="decreto:VehiculoUsadoEnajenadoPermAlFab">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@PrecioVehUsado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoVeh" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Marca" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipooClase" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Modelo" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NIV" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumSerie" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPlacas" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumMotor" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumFolTarjCir" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumFolAvisoint" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPedIm" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Aduana" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaRegulVeh" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Foliofiscal" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="destruccion:certificadodedestruccion">
    <!--Manejador de nodos tipo certificadodedestruccion-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Serie" />
    </xsl:call-template>
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumFolDesVeh" />
    </xsl:call-template>
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:apply-templates select="./destruccion:VehiculoDestruido" />
    <xsl:apply-templates select="./destruccion:InformacionAduanera" />
  </xsl:template>
  <xsl:template match="destruccion:VehiculoDestruido">
    <!--  Iniciamos el tratamiento de los atributos de destruccion:VehiculoDestruido-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Marca" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipooClase" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Modelo" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NIV" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumSerie" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPlacas" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumMotor" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumFolTarjCir" />
    </xsl:call-template>
  </xsl:template>
  <xsl:template match="destruccion:InformacionAduanera">
    <!--  Iniciamos el tratamiento de los atributos de destruccion:InformaciónAduanera-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumPedImp" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Fecha" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Aduana" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="obrasarte:obrasarteantiguedades">
    <!--Manejador de nodos tipo obrasarteantiguedades-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoBien" />
    </xsl:call-template>
	<xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@OtrosTipoBien" />
    </xsl:call-template>
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TituloAdquirido" />
    </xsl:call-template>
	<xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@OtrosTituloAdquirido" />
    </xsl:call-template>
	<xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Subtotal" />
    </xsl:call-template>
	<xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@IVA" />
    </xsl:call-template>
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaAdquisicion" />
    </xsl:call-template>
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@CaracterísticasDeObraoPieza" />
    </xsl:call-template>
	</xsl:template>

  <xsl:template match="cce11:ComercioExterior">
    <!--Manejador de nodos tipo ComercioExterior-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MotivoTraslado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ClaveDePedimento" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CertificadoOrigen" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumCertificadoOrigen" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumeroExportadorConfiable" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Incoterm" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Subdivision" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Observaciones" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoCambioUSD" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalUSD" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./cce11:Emisor" />
    <xsl:for-each select="./cce11:Propietario">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:apply-templates select="./cce11:Receptor" />
    <xsl:for-each select="./cce11:Destinatario">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:apply-templates select="./cce11:Mercancias" />
  </xsl:template>

  <xsl:template match="cce11:Emisor">
    <!--  Iniciamos el tratamiento de los atributos de cce11:Emisor-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Curp" />
    </xsl:call-template>

    <xsl:apply-templates select="./cce11:Domicilio" />

  </xsl:template>

  <xsl:template match="cce11:Propietario">
    <!--  Tratamiento de los atributos de cce11:Propietario-->
    
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumRegIdTrib" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ResidenciaFiscal" />
    </xsl:call-template>

  </xsl:template>

  <xsl:template match="cce11:Receptor">
    <!--  Tratamiento de los atributos de cce11:Receptor-->
    
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumRegIdTrib" />
    </xsl:call-template>
    <xsl:apply-templates select="./cce11:Domicilio" />

  </xsl:template>

  <xsl:template match="cce11:Destinatario">
    <!--  Tratamiento de los atributos de cce11:Destinatario-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumRegIdTrib" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Nombre" />
    </xsl:call-template>
    <!--  Manejo de los nodos dependientes -->
    <xsl:for-each select="./cce11:Domicilio">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="cce11:Mercancias">
   <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:for-each select="./cce11:Mercancia">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="cce11:Domicilio">
    <!--  Iniciamos el tratamiento de los atributos de cce11:Domicilio-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Calle" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumeroExterior" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumeroInterior" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Colonia" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Localidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Referencia" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Municipio" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Estado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Pais" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@CodigoPostal" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="cce11:Mercancia">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NoIdentificacion" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FraccionArancelaria" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CantidadAduana" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@UnidadAduana" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ValorUnitarioAduana" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ValorDolares" />
    </xsl:call-template>
	 <xsl:for-each select="./cce11:DescripcionesEspecificas">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

    <xsl:template match="cce11:DescripcionesEspecificas">
      <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Marca" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Modelo" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SubModelo" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumeroSerie" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="ine:INE">
    <!--Manejador de nodos tipo INE-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoProceso" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoComite" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@IdContabilidad" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:for-each select="./ine:Entidad">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="ine:Entidad">
    <!--Manejador de nodos tipo Entidad-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveEntidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Ambito" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de ine:Contabilidad-->
    <xsl:for-each select="./ine:Contabilidad">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Contabilidad-->
  <xsl:template match="ine:Contabilidad">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IdContabilidad" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="ine:INE">
    <!--Manejador de nodos tipo INE-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoProceso" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoComite" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@IdContabilidad" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:for-each select="./ine:Entidad">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="ine:Entidad">
    <!--Manejador de nodos tipo Entidad-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveEntidad" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Ambito" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de ine:Contabilidad-->
    <xsl:for-each select="./ine:Contabilidad">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Contabilidad-->
  <xsl:template match="ine:Contabilidad">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IdContabilidad" />
    </xsl:call-template>
  </xsl:template>

	<!-- Manejador de nodos tipo iedu -->
	<xsl:template match="iedu:instEducativas">
		<!--Iniciamos el tratamiento de los atributos de instEducativas -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@nombreAlumno"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@CURP"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@nivelEducativo"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@autRVOE"/>
		</xsl:call-template>
			<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@rfcPago"/>
		</xsl:call-template>
	</xsl:template>

	<!-- Manejador de nodos tipo VentaVehiculos-->
	<xsl:template match="ventavehiculos:VentaVehiculos">
		<!--Iniciamos el tratamiento de los atributos del complemento concepto VentaVehiculos-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@ClaveVehicular"/>
		</xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Niv"/>
    </xsl:call-template>
		<!-- Manejo de los atributos de la información aduanera del complemento de terceros -->
		<xsl:for-each select=".//ventavehiculos:InformacionAduanera">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!-- Manejador de nodos tipo Información Aduanera -->
	<xsl:template match="ventavehiculos:InformacionAduanera">
		<!-- Manejo de los atributos de la información aduanera -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@numero"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@fecha"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@aduana"/>
		</xsl:call-template>
	</xsl:template>

	
    <!-- Manejador de nodos tipo detallista -->
	<xsl:template match="detallista:detallista">
		<!-- Iniciamos el tratamiento de los atributos del sector detallista -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@documentStructureVersion"/>
		</xsl:call-template>
		<xsl:for-each select="detallista:orderIdentification/detallista:referenceIdentification">
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="."/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="detallista:orderIdentification/detallista:ReferenceDate"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="detallista:buyer/detallista:gln"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="detallista:seller/detallista:gln"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="detallista:seller/detallista:alternatePartyIdentification"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="detallista:totalAmount/detallista:Amount"/>
		</xsl:call-template>
		<xsl:for-each select="detallista:TotalAllowanceCharge/detallista:specialServicesType">
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="."/>
			</xsl:call-template>
		</xsl:for-each>
		<xsl:for-each select="detallista:TotalAllowanceCharge/detallista:Amount">
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="."/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>

  <xsl:template match="ecc12:EstadoDeCuentaCombustible">
    <!--Manejador de nodos tipo EstadoDeCuentaCombustible-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumeroDeCuenta" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@SubTotal" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Total" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./ecc12:Conceptos" />
  </xsl:template>


  <xsl:template match="ecc12:Conceptos">
    <!--  Iniciamos el tratamiento de los atributos de ecc12:ConceptoEstadoDeCuentaCombustible-->
    <xsl:for-each select="./ecc12:ConceptoEstadoDeCuentaCombustible">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
  </xsl:template>

  <xsl:template match="ecc12:Traslados">
    <!--  Iniciamos el tratamiento de los atributos de ecc12:Traslado-->
    <xsl:for-each select="./ecc12:Traslado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  
  <!--  Iniciamos el manejo de los elementos hijo en la secuencia ConceptoEstadoDeCuentaCombustible-->
  <xsl:template match="ecc12:ConceptoEstadoDeCuentaCombustible">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Identificador" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Fecha" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Rfc" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveEstacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Cantidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Unidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NombreCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FolioOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ValorUnitario" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./ecc12:Traslados" />

  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Traslado-->
  <xsl:template match="ecc12:Traslado">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Impuesto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TasaOCuota" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe" />
    </xsl:call-template>
  </xsl:template>

    <xsl:template match="consumodecombustibles11:ConsumoDeCombustibles">
    <!--Manejador de nodos tipo ConsumoDeCombustibles-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@numeroDeCuenta" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@subTotal" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@total" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./consumodecombustibles11:Conceptos" />
  </xsl:template>


  <xsl:template match="consumodecombustibles11:Conceptos">
    <!--  Iniciamos el tratamiento de los atributos de consumodecombustibles11:ConceptoConsumoDeCombustibles-->
    <xsl:for-each select="./consumodecombustibles11:ConceptoConsumoDeCombustibles">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
  </xsl:template>

  <!--  Iniciamos el manejo de los elementos hijo en la secuencia ConceptoConsumoDeCombustibles-->
  <xsl:template match="consumodecombustibles11:ConceptoConsumoDeCombustibles">
    <!--  Iniciamos el manejo de los nodos dependientes -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@identificador" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@fecha" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@rfc" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@claveEstacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tipoCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@cantidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@nombreCombustible" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@folioOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@valorUnitario" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@importe" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:apply-templates select="./consumodecombustibles11:Determinados" />

  </xsl:template>

    <xsl:template match="consumodecombustibles11:Determinados">
    <!--  Iniciamos el tratamiento de los atributos de consumodecombustibles:Determinado-->
    <xsl:for-each select="./consumodecombustibles11:Determinado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  
  <!--  Iniciamos el manejo de los elementos hijo en la secuencia Determinado-->
  <xsl:template match="consumodecombustibles11:Determinado">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@impuesto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@tasaOCuota" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@importe" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="gceh:GastosHidrocarburos">
    <!--Manejador de Atributos GastosHidrocarburos-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumeroContrato" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@AreaContractual" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:for-each select="./gceh:Erogacion">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="gceh:Erogacion">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoErogacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MontocuErogacion" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Porcentaje" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de gceh:DocumentoRelacionado-->
    <xsl:for-each select="./gceh:DocumentoRelacionado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:for-each select="./gceh:Actividades">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    <xsl:for-each select="./gceh:CentroCostos">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!--  Iniciamos el tratamiento de los atributos de gceh:DocumentoRelacionado-->
  <xsl:template match="gceh:DocumentoRelacionado">

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@OrigenErogacion" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FolioFiscalVinculado" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RFCProveedor" />
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MontoTotalIVA" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoRetencionISR" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoRetencionIVA" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoRetencionOtrosImpuestos" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumeroPedimentoVinculado" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ClavePedimentoVinculado" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ClavePagoPedimentoVinculado" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MontoIVAPedimento" />
    </xsl:call-template>

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@OtrosImpuestosPagadosPedimento" />
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaFolioFiscalVinculado" />
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Mes" />
    </xsl:call-template>

    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MontoTotalErogaciones" />
    </xsl:call-template>

  </xsl:template>

  <!--  Iniciamos el tratamiento de los atributos de gceh:Actividades-->
  <xsl:template match="gceh:Actividades">
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ActividadRelacionada" />
    </xsl:call-template>

    <xsl:for-each select="./gceh:SubActividades">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

 </xsl:template>

  <xsl:template match="gceh:SubActividades">
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SubActividadRelacionada" />
    </xsl:call-template>

    <xsl:for-each select="./gceh:Tareas">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="gceh:Tareas">
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TareaRelacionada" />
    </xsl:call-template>
  </xsl:template>

  <!--  Iniciamos el tratamiento de los atributos de gceh:CentroCostos-->
  <xsl:template match="gceh:CentroCostos">

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Campo" />
    </xsl:call-template>

    <xsl:for-each select="./gceh:Yacimientos">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <xsl:template match="gceh:Yacimientos">

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Yacimiento" />
    </xsl:call-template>

    <xsl:for-each select="./gceh:Pozos">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

  </xsl:template>

  <xsl:template match="gceh:Pozos">

    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Pozo" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="ieeh:IngresosHidrocarburos">
    <!--Manejador de Atributos IngresosHidrocarburos-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumeroContrato" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ContraprestacionPagadaOperador" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Porcentaje" />
    </xsl:call-template>

    <!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
    <xsl:for-each select="./ieeh:DocumentoRelacionado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="ieeh:DocumentoRelacionado">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FolioFiscalVinculado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaFolioFiscalVinculado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Mes" />
    </xsl:call-template>

  </xsl:template>

	<xsl:template match="cartaporte20:CartaPorte">
		<!--Manejador de nodos tipo CartaPorte-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Version"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TranspInternac"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@EntradaSalidaMerc"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PaisOrigenDestino"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ViaEntradaSalida"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@TotalDistRec"/>
		</xsl:call-template>
		<!--  Iniciamos el manejo de los elementos hijo en la secuencia -->
		<xsl:for-each select="./cartaporte20:Ubicaciones">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<xsl:for-each select="./cartaporte20:Mercancias">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<xsl:for-each select="./cartaporte20:FiguraTransporte">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Ubicaciones-->
	<xsl:template match="cartaporte20:Ubicaciones">
		<!--  Iniciamos el tratamiento de los atributos de Ubicacion-->
		<xsl:for-each select="./cartaporte20:Ubicacion">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Ubicacion-->
	<xsl:template match="cartaporte20:Ubicacion">
		<!--Manejador de nodos tipo Ubicacion-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoUbicacion"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@IDUbicacion"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@RFCRemitenteDestinatario"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreRemitenteDestinatario"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumRegIdTrib"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ResidenciaFiscal"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumEstacion"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreEstacion"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NavegacionTrafico"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@FechaHoraSalidaLlegada"/>
		</xsl:call-template>		
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@TipoEstacion"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@DistanciaRecorrida"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Domicilio-->
		<xsl:for-each select="./cartaporte20:Domicilio">
			<!--  Iniciamos el manejo de los elementos hijo en la secuencia Domicilio-->
			<!--  Iniciamos el manejo de los nodos dependientes -->
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Calle"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@NumeroExterior"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@NumeroInterior"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Colonia"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Localidad"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Referencia"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Municipio"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Estado"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Pais"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@CodigoPostal"/>
			</xsl:call-template>		
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Origen-->
	<xsl:template match="cartaporte20:Mercancias">
		<!--Manejador de nodos tipo cartaporte20:Mercancias-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoBrutoTotal"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@UnidadPeso"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PesoNetoTotal"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumTotalMercancias"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@CargoPorTasacion"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Mercancia-->
		<xsl:for-each select="./cartaporte20:Mercancia">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Autotransporte-->
		<xsl:for-each select="./cartaporte20:Autotransporte">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:TransporteMaritimo-->
		<xsl:for-each select="./cartaporte20:TransporteMaritimo">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:TransporteAereo-->
		<xsl:for-each select="./cartaporte20:TransporteAereo">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:TransporteFerroviario-->
		<xsl:for-each select="./cartaporte20:TransporteFerroviario">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Mercancia-->
	<xsl:template match="cartaporte20:Mercancia">
		<!--Manejador de nodos tipo cartaporte20:Mercancia-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@BienesTransp"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ClaveSTCC"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Descripcion"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Cantidad"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@ClaveUnidad"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Unidad"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Dimensiones"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@MaterialPeligroso"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@CveMaterialPeligroso"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Embalaje"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@DescripEmbalaje"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoEnKg"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ValorMercancia"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Moneda"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@FraccionArancelaria"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@UUIDComercioExt"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Pedimentos-->
		<xsl:for-each select="./cartaporte20:Pedimentos">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:GuiasIdentificacion-->
		<xsl:for-each select="./cartaporte20:GuiasIdentificacion">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:CantidadTransporta-->
		<xsl:for-each select="./cartaporte20:CantidadTransporta">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:DetalleMercancia-->
		<xsl:for-each select="./cartaporte20:DetalleMercancia">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Pedimentos-->
	<xsl:template match="cartaporte20:Pedimentos">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Pedimento"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia GuiasIdentificacion-->
	<xsl:template match="cartaporte20:GuiasIdentificacion--">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumeroGuiaIdentificacion"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@DescripGuiaIdentificacion"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoGuiaIdentificacion"/>
		</xsl:call-template>
	</xsl:template>	
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia CantidadTransporta-->
	<xsl:template match="cartaporte20:CantidadTransporta">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Cantidad"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@IDOrigen"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@IDDestino"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@CvesTransporte"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia DetalleMercancia-->
	<xsl:template match="cartaporte20:DetalleMercancia">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@UnidadPesoMerc"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoBruto"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoNeto"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PesoTara"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumPiezas"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia AutotransporteFederal-->
	<xsl:template match="cartaporte20:Autotransporte">
		<!--Manejador de nodos tipo cartaporte20:AutotransporteFederal-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PermSCT"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumPermisoSCT"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:IdentificacionVehicular-->
		<xsl:for-each select="./cartaporte20:IdentificacionVehicular">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Seguros-->
		<xsl:for-each select="./cartaporte20:Seguros">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Remolques-->
		<xsl:for-each select="./cartaporte20:Remolques">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia IdentificacionVehicular-->
	<xsl:template match="cartaporte20:IdentificacionVehicular">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@ConfigVehicular"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PlacaVM"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@AnioModeloVM"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Seguros-->
	<xsl:template match="cartaporte20:Seguros">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@AseguraRespCivil"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PolizaRespCivil"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@AseguraMedAmbiente"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PolizaMedAmbiente"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@AseguraCarga"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PolizaCarga"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PrimaSeguro"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Remolques-->
	<xsl:template match="cartaporte20:Remolques">
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Remolque-->
		<xsl:for-each select="./cartaporte20:Remolque">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Remolque-->
	<xsl:template match="cartaporte20:Remolque">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@SubTipoRem"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Placa"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia TransporteMaritimo-->
	<xsl:template match="cartaporte20:TransporteMaritimo">
		<!--Manejador de nodos tipo cartaporte20:TransporteMaritimo-->
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@PermSCT"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumPermisoSCT"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreAseg"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumPolizaSeguro"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoEmbarcacion"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@Matricula"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumeroOMI"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@AnioEmbarcacion"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreEmbarc"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NacionalidadEmbarc"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@UnidadesDeArqBruto"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoCarga"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumCertITC"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Eslora"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Manga"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@Calado"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@LineaNaviera"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NombreAgenteNaviero"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumAutorizacionNaviero"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumViaje"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumConocEmbarc"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Contenedor-->
		<xsl:for-each select="./cartaporte20:Contenedor">
			<!--  Iniciamos el manejo de los elementos hijo en la secuencia Contenedor-->
			<!--  Iniciamos el manejo de los nodos dependientes -->
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@MatriculaContenedor"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@TipoContenedor"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@NumPrecinto"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia TransporteAereo-->
	<xsl:template match="cartaporte20:TransporteAereo">
		<!--Manejador de nodos tipo cartaporte20:TransporteAereo-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@PermSCT"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumPermisoSCT"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@MatriculaAeronave"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreAseg"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumPolizaSeguro"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@NumeroGuia"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@LugarContrato"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@CodigoTransportista"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@RFCEmbarcador"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumRegIdTribEmbarc"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ResidenciaFiscalEmbarc"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreEmbarcador"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia TransporteFerroviario-->
	<xsl:template match="cartaporte20:TransporteFerroviario">
		<!--Manejador de nodos tipo cartaporte20:TransporteFerroviario-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoDeServicio"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoDeTrafico"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreAseg"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumPolizaSeguro"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:DerechosDePaso-->
		<xsl:for-each select="./cartaporte20:DerechosDePaso">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Carro-->
		<xsl:for-each select="./cartaporte20:Carro">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia DerechosDePaso-->
	<xsl:template match="cartaporte20:DerechosDePaso">
		<!--  Iniciamos el manejo de los nodos dependientes -->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoDerechoDePaso"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@KilometrajePagado"/>
		</xsl:call-template>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia Carro-->
	<xsl:template match="cartaporte20:Carro">
		<!--Manejador de nodos tipo cartaporte20:Carro-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoCarro"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@MatriculaCarro"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@GuiaCarro"/>
		</xsl:call-template>
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@ToneladasNetasCarro"/>
		</xsl:call-template>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Contenedor -->
		<xsl:for-each select="./cartaporte20:Contenedor ">
			<!--  Iniciamos el manejo de los elementos hijo en la secuencia Contenedor-->
			<!--  Iniciamos el manejo de los nodos dependientes -->
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@TipoContenedor"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@PesoContenedorVacio"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@PesoNetoMercancia"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia FiguraTransporte-->
	<xsl:template match="cartaporte20:FiguraTransporte">
		<!--Manejador de nodos tipo cartaporte20:FiguraTransporte-->
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:TiposFigura-->
		<xsl:for-each select="./cartaporte20:TiposFigura ">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia TiposFigura-->
	<xsl:template match="cartaporte20:TiposFigura">
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:TiposFigura-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@TipoFigura"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@RFCFigura"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumLicencia"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NombreFigura"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@NumRegIdTribFigura"/>
		</xsl:call-template>
		<xsl:call-template name="Opcional">
			<xsl:with-param name="valor" select="./@ResidenciaFiscalFigura"/>
		</xsl:call-template>
		<xsl:for-each select="./cartaporte20:PartesTransporte">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		<!--  Iniciamos el tratamiento de los atributos de cartaporte20:Domicilio -->
		<xsl:for-each select="./cartaporte20:Domicilio ">
			<!--  Iniciamos el manejo de los elementos hijo en la secuencia Domicilio-->
			<!--  Iniciamos el manejo de los nodos dependientes -->
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Calle"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@NumeroExterior"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@NumeroInterior"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Colonia"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Localidad"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Referencia"/>
			</xsl:call-template>
			<xsl:call-template name="Opcional">
				<xsl:with-param name="valor" select="./@Municipio"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Estado"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@Pais"/>
			</xsl:call-template>
			<xsl:call-template name="Requerido">
				<xsl:with-param name="valor" select="./@CodigoPostal"/>
			</xsl:call-template>
		</xsl:for-each>
	</xsl:template>
	<!--  Iniciamos el manejo de los elementos hijo en la secuencia PartesTransporte-->
	<xsl:template match="cartaporte20:PartesTransporte">
		<!--Manejador de nodos tipo cartaporte20:PartesTransporte-->
		<xsl:call-template name="Requerido">
			<xsl:with-param name="valor" select="./@ParteTransporte"/>
		</xsl:call-template>
		</xsl:template>

  <xsl:template match="pago20:Pagos">
    <!--Manejador de Atributos Pagos-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version" />
    </xsl:call-template>

    <!-- Iniciamos el manejo de los elementos de tipo Totales. -->
    <xsl:for-each select="./pago20:Totales">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

	<!-- Iniciamos el manejo de los elementos de tipo Pago. -->
    <xsl:for-each select="./pago20:Pago">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <!-- Iniciamos el tratamiento de los atributos de pago20:Totales. -->
  <xsl:template match="pago20:Totales">
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalRetencionesIVA" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalRetencionesISR" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalRetencionesIEPS" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosBaseIVA16" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosImpuestoIVA16" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosBaseIVA8" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosImpuestoIVA8" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosBaseIVA0" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosImpuestoIVA0" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalTrasladosBaseIVAExento" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MontoTotalPagos" />
    </xsl:call-template>
  </xsl:template>

  <!-- Iniciamos el tratamiento de los atributos de pago20:Pago -->
  <xsl:template match="pago20:Pago">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FechaPago" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@FormaDePagoP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MonedaP" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoCambioP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Monto" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumOperacion" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RfcEmisorCtaOrd" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NomBancoOrdExt" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CtaOrdenante" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@RfcEmisorCtaBen" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CtaBeneficiario" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoCadPago" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CertPago" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CadPago" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@SelloPago" />
    </xsl:call-template>

    <!--  Iniciamos el tratamiento de los atributos de pago20:DocumentoRelacionado. -->
    <xsl:for-each select="./pago20:DoctoRelacionado">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
    
    <!--  Iniciamos el tratamiento de los atributos de pago20:ImpuestosP. -->
    <xsl:for-each select="./pago20:ImpuestosP">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!-- Iniciamos el tratamiento de los atributos de pago20:DoctoRelacionado. -->
  <xsl:template match="pago20:DoctoRelacionado">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@IdDocumento" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Serie" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Folio" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@MonedaDR" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@EquivalenciaDR" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumParcialidad" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImpSaldoAnt" />
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ImpPagado" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImpSaldoInsoluto" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ObjetoImpDR" />
    </xsl:call-template>

	<!--  Iniciamos el tratamiento de los atributos del subnodo ImpuestosDR-RetencionesDR-RetencionDR. -->
	<xsl:for-each select="./pago20:ImpuestosDR/pago20:RetencionesDR/pago20:RetencionDR">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@BaseDR"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
		<xsl:with-param name="valor" select="./@ImpuestoDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Requerido">
		<xsl:with-param name="valor" select="./@TipoFactorDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Requerido">
	    <xsl:with-param name="valor" select="./@TasaOCuotaDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Requerido">
	    <xsl:with-param name="valor" select="./@ImporteDR" />
	  </xsl:call-template>
    </xsl:for-each>
    
    <!--  Iniciamos el tratamiento de los atributos del subnodo ImpuestosDR-TrasladosDR-TrasladoDR. -->
    <xsl:for-each select="./pago20:ImpuestosDR/pago20:TrasladosDR/pago20:TrasladoDR">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@BaseDR"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
		<xsl:with-param name="valor" select="./@ImpuestoDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Requerido">
		<xsl:with-param name="valor" select="./@TipoFactorDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Opcional">
	    <xsl:with-param name="valor" select="./@TasaOCuotaDR" />
	  </xsl:call-template>
	  <xsl:call-template name="Opcional">
	    <xsl:with-param name="valor" select="./@ImporteDR" />
	  </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <!-- Iniciamos el tratamiento de los atributos de pago20:ImpuestosP. -->
  <xsl:template match="pago20:ImpuestosP">
    <xsl:apply-templates select="./pago20:RetencionesP"/>
    <xsl:apply-templates select="./pago20:TrasladosP"/>
  </xsl:template>

  <xsl:template match="pago20:RetencionesP">
    <xsl:for-each select="./pago20:RetencionP">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="pago20:TrasladosP">
    <xsl:for-each select="./pago20:TrasladoP">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <xsl:template match="pago20:RetencionP">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImpuestoP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImporteP" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="pago20:TrasladoP">
	<xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@BaseP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImpuestoP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoFactorP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TasaOCuotaP" />
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ImporteP" />
    </xsl:call-template>
  </xsl:template>

  <!-- Aquí iniciamos el procesamiento de la cadena original con su | inicial y el terminador || -->
  <xsl:template match="/">|<xsl:apply-templates select="/cfdi:Comprobante"/>||</xsl:template>
  <!--  Aquí iniciamos el procesamiento de los datos incluidos en el comprobante -->
  <xsl:template match="cfdi:Comprobante">
    <!-- Iniciamos el tratamiento de los atributos de comprobante -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Version"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Serie"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Folio"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Fecha"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FormaPago"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NoCertificado"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@CondicionesDePago"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@SubTotal"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Descuento"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Moneda"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TipoCambio"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Total"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoDeComprobante"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Exportacion"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@MetodoPago"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@LugarExpedicion"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Confirmacion"/>
    </xsl:call-template>
    <!--
		Llamadas para procesar al los sub nodos del comprobante
	-->
	<xsl:apply-templates select="./cfdi:InformacionGlobal"/>
    <xsl:apply-templates select="./cfdi:CfdiRelacionados"/>
    <xsl:apply-templates select="./cfdi:Emisor"/>
    <xsl:apply-templates select="./cfdi:Receptor"/>
    <xsl:apply-templates select="./cfdi:Conceptos"/>
    <xsl:apply-templates select="./cfdi:Impuestos"/>
    <xsl:for-each select="./cfdi:Complemento">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <!-- Manejador de nodos tipo InformacionGlobal -->
  <xsl:template match="cfdi:InformacionGlobal">
    <!-- Iniciamos el tratamiento de los atributos del nodo tipo InformacionGlobal -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Periodicidad"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Meses"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Año"/>
    </xsl:call-template>
  </xsl:template>

  <!-- Manejador de nodos tipo CFDIRelacionados -->
  <xsl:template match="cfdi:CfdiRelacionados">
    <!-- Iniciamos el tratamiento de los atributos del nodo tipo CFDIRelacionados -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@TipoRelacion"/>
    </xsl:call-template>
    <xsl:for-each select="./cfdi:CfdiRelacionado">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@UUID"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>

  <!-- Manejador de nodos tipo Emisor -->
  <xsl:template match="cfdi:Emisor">
    <!-- Iniciamos el tratamiento de los atributos del nodo tipo Emisor -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Rfc"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Nombre"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RegimenFiscal"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@FacAtrAdquirente"/>
    </xsl:call-template>
  </xsl:template>

  <!-- Manejador de nodos tipo Receptor -->
  <xsl:template match="cfdi:Receptor">
    <!-- Iniciamos el tratamiento de los atributos del nodo tipo Receptor -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Rfc"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Nombre"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@DomicilioFiscalReceptor"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ResidenciaFiscal"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NumRegIdTrib"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RegimenFiscalReceptor"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@UsoCFDI"/>
    </xsl:call-template>

  </xsl:template>

  <!-- Manejador de nodos tipo Conceptos -->
  <xsl:template match="cfdi:Conceptos">
    <!-- Llamada para procesar los distintos nodos tipo Concepto -->
    <xsl:for-each select="./cfdi:Concepto">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!--Manejador de nodos tipo Concepto-->
  <xsl:template match="cfdi:Concepto">
    <!-- Iniciamos el tratamiento de los atributos del Concepto -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveProdServ"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NoIdentificacion"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Cantidad"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveUnidad"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Unidad"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Descripcion"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ValorUnitario"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Importe"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Descuento"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ObjetoImp"/>
    </xsl:call-template>

    <!-- Manejo de sub nodos de información Traslado de Conceptos:Concepto:Impuestos:Traslados-->
    <xsl:for-each select="./cfdi:Impuestos/cfdi:Traslados/cfdi:Traslado">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Base"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Impuesto"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@TipoFactor"/>
      </xsl:call-template>
      <xsl:call-template name="Opcional">
        <xsl:with-param name="valor" select="./@TasaOCuota"/>
      </xsl:call-template>
      <xsl:call-template name="Opcional">
        <xsl:with-param name="valor" select="./@Importe"/>
      </xsl:call-template>
    </xsl:for-each>

    <!-- Manejo de sub nodos de Retencion por cada una de los Conceptos:Concepto:Impuestos:Retenciones-->
    <xsl:for-each select="./cfdi:Impuestos/cfdi:Retenciones/cfdi:Retencion">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Base"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Impuesto"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@TipoFactor"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@TasaOCuota"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Importe"/>
      </xsl:call-template>
    </xsl:for-each>

	<!-- Manejo de los distintos sub nodos a cuenta de terceros de forma indistinta a su grado de dependencia -->
    <xsl:for-each select="./cfdi:ACuentaTerceros">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <!-- Manejo de los distintos sub nodos de información aduanera de forma indistinta a su grado de dependencia -->
    <xsl:for-each select="./cfdi:InformacionAduanera">
      <xsl:apply-templates select="."/>
    </xsl:for-each>

    <!-- Llamada al manejador de nodos de CuentaPredial en caso de existir -->
    <xsl:if test="./cfdi:CuentaPredial">
      <xsl:apply-templates select="./cfdi:CuentaPredial"/>
    </xsl:if>

    <!-- Llamada al manejador de nodos de ComplementoConcepto en caso de existir -->
    <xsl:if test="./cfdi:ComplementoConcepto">
      <xsl:apply-templates select="./cfdi:ComplementoConcepto"/>
    </xsl:if>

    <!-- Llamada al manejador de nodos de Parte en caso de existir -->
    <xsl:for-each select=".//cfdi:Parte">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>
  
  <!-- Manejador de nodos tipo ACuentaTerceros -->
  <xsl:template match="cfdi:ACuentaTerceros">
    <!-- Manejo de los atributos del nodo tipo ACuentaTerceros -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RfcACuentaTerceros"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NombreACuentaTerceros"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@RegimenFiscalACuentaTerceros"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@DomicilioFiscalACuentaTerceros"/>
    </xsl:call-template>
  </xsl:template>

  <!-- Manejador de nodos tipo Información Aduanera -->
  <xsl:template match="cfdi:InformacionAduanera">
    <!-- Manejo de los atributos de la información aduanera -->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@NumeroPedimento"/>
    </xsl:call-template>
  </xsl:template>

  <!-- Manejador de nodos tipo Información CuentaPredial -->
  <xsl:template match="cfdi:CuentaPredial">
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Numero"/>
    </xsl:call-template>
  </xsl:template>

  <!-- Manejador de nodos tipo ComplementoConcepto -->
  <xsl:template match="cfdi:ComplementoConcepto">
    <xsl:for-each select="./*">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!-- Manejador de nodos tipo Parte -->
  <xsl:template match="cfdi:Parte">
    <!-- Iniciamos el tratamiento de los atributos de Parte-->
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@ClaveProdServ"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@NoIdentificacion"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Cantidad"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Unidad"/>
    </xsl:call-template>
    <xsl:call-template name="Requerido">
      <xsl:with-param name="valor" select="./@Descripcion"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@ValorUnitario"/>
    </xsl:call-template>
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@Importe"/>
    </xsl:call-template>

    <!-- Manejador de nodos tipo InformacionAduanera-->
    <xsl:for-each select=".//cfdi:InformacionAduanera">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!-- Manejador de nodos tipo Complemento -->
  <xsl:template match="cfdi:Complemento">
    <xsl:for-each select="./*">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </xsl:template>

  <!-- Manejador de nodos tipo Domicilio fiscal -->
  <xsl:template match="cfdi:Impuestos">
    <!-- Manejo de sub nodos de Retencion por cada una de los Impuestos:Retenciones-->
    <xsl:for-each select="./cfdi:Retenciones/cfdi:Retencion">
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Impuesto"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Importe"/>
      </xsl:call-template>
    </xsl:for-each>
    <!-- Iniciamos el tratamiento de los atributos de TotalImpuestosRetenidos-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalImpuestosRetenidos"/>
    </xsl:call-template>
    <!-- Manejo de sub nodos de información Traslado de Impuestos:Traslados-->
    <xsl:for-each select="./cfdi:Traslados/cfdi:Traslado">
	  <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Base"/>
      </xsl:call-template>      
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Impuesto"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@TipoFactor"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@TasaOCuota"/>
      </xsl:call-template>
      <xsl:call-template name="Requerido">
        <xsl:with-param name="valor" select="./@Importe"/>
      </xsl:call-template>
    </xsl:for-each>
    <!-- Iniciamos el tratamiento de los atributos de TotalImpuestosTrasladados-->
    <xsl:call-template name="Opcional">
      <xsl:with-param name="valor" select="./@TotalImpuestosTrasladados"/>
    </xsl:call-template>
  </xsl:template>
</xsl:stylesheet>