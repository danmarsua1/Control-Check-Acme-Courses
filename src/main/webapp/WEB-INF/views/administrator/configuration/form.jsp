<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>


<acme:form>
	<acme:input-textbox
		code="administrator.configuration.configuration.form.label.currency"
		path="currency" />
	<acme:input-textbox readonly="true"
		code="administrator.configuration.configuration.form.label.acceptedCurrencies"
		path="acceptedCurrencies" />
	<acme:input-double
		code="administrator.configuration.configuration.form.label.spamThreshold"
		path="spamThreshold" />
	<acme:input-double
		code="administrator.configuration.configuration.form.label.spamBooster"
		path="spamBooster" />

	<fieldset>
		<legend>
			<acme:message
				code="administrator.configuration.configuration.form.label.spamRecords" />
		</legend>
		<table class="table table-sm">
			<tr>
				<th><acme:message
						code="administrator.configuration.configuration.form.label.spamRecords.term" /></th>
				<th><acme:message
						code="administrator.configuration.configuration.form.label.spamRecords.weight" /></th>
				<th><acme:message
						code="administrator.configuration.configuration.form.label.spamRecords.boosterTerm" /></th>
			</tr>
			<jstl:forEach items="${spamRecordObjects}" var="record">
				<tr>
					<td><acme:print value="${record.term}" /></td>
					<td><acme:print value="${record.weight}" /></td>
					<jstl:choose>
						<jstl:when test="${record.boosterTerm eq 'X'}">
							<td><acme:message
								code="administrator.configuration.configuration.form.label.spamRecords.boosterTerm.undefined" /></td>
						</jstl:when>
						<jstl:otherwise>
							<td><acme:print value="${record.boosterTerm}" /></td>
						</jstl:otherwise>
					</jstl:choose>
				</tr>
			</jstl:forEach>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>
			<acme:message
				code="administrator.configuration.configuration.form.label.newSpamRecord" />
		</legend>
		<acme:input-textbox
			code="administrator.configuration.configuration.form.label.spamRecords.term" path="newTerm" />
		<acme:input-double
			code="administrator.configuration.configuration.form.label.spamRecords.weight" path="newWeight" />
		<acme:input-textbox
			code="administrator.configuration.configuration.form.label.spamRecords.boosterTerm" path="newBoosterTerm" />
	</fieldset>
	<acme:submit code="administrator.configuration.configuration.form.button.update" action="/administrator/configuration/update"/>
</acme:form>