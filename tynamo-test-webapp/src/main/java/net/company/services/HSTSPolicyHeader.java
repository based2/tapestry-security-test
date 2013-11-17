package net.company.services;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import java.io.IOException;

/**
 * HTTPS only: No HTTP allowed
 *
 * HSTS Policy specifies a period of time during which the user agent
 * shall access the server in only secure fashion (HTTP -> HTTPS).
 *
 * http://justinself.com/http-security-headers-you-should-be-thinking-about-htst/
 *
 * https://www.owasp.org/index.php/HTTP_Strict_Transport_Security netsec,ssl,hsts
 * http://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security netsec,ssl,hsts
 * http://www.bortzmeyer.org/6797.html netsec,ssl,hsts,fr
 *
 * Limits:
 * Browser 	            Support Introduced
 * =======================================
 *  Internet Explorer 	no support as of IE 10 (tested on 2013-01-01)
 *  Firefox 	        4
 *  Opera 	            12
 *  Safari 	            no support
 *  Chrome 	            4.0.211.0
 *
 * http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/t5-adding-http-header-td3369097.html
 *
 * https://github.com/twitter/secureheaders
 * http://www.browserscope.org/?category=security
 */
public class HSTSPolicyHeader implements RequestFilter
{
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";

    public boolean service(Request request, Response response,  RequestHandler handler) throws IOException
    {
        response.setHeader(STRICT_TRANSPORT_SECURITY, "max-age=60000; includeSubDomains");
        return handler.service(request, response);
    }
}