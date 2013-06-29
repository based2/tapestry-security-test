/**
 ******************************************************************************
 *      Atos Worldline (Blois)          Offering and Development
 *      19 rue de la Vallee Maillard      Phone +33 (0) 2.54.44.70.00
 *      41 013 Blois cedex
 *
 *      Atos Worldline is an Atos Origin company
 *      All rights reserved
 *
 ******************************************************************************
 */
package net.company.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.internal.services.LoginContextService;


public class AccessDenied {
	
	@Inject
	private LoginContextService loginContextService;

	public String getSuccess() {
		return loginContextService.getSuccessPage();
	}
	
}
