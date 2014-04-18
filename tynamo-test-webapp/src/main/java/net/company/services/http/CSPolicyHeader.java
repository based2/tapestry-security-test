package net.company.services.http;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import java.io.IOException;

/**
 * Restrict JS execution
 *
 * Content Security Policy for web applications mechanism can be used to mitigate
 * a broad class of content injection vulnerabilities, such as cross-site scripting (XSS).
 *
 * http://en.wikipedia.org/wiki/Content_Security_Policy
 * https://www.owasp.org/index.php/Content_Security_Policy
 * http://developer.chrome.com/extensions/contentSecurityPolicy.html
 * https://dvcs.w3.org/hg/content-security-policy/raw-file/tip/csp-specification.dev.html
 *
 * https://github.com/twitter/secureheaders
 * http://www.browserscope.org/?category=security
 */
public class CSPolicyHeader implements RequestFilter
{
    private static final String STRICT_TRANSPORT_SECURITY = "content_security_policy";

    public boolean service(Request request, Response response,  RequestHandler handler) throws IOException
    {
        response.setHeader(STRICT_TRANSPORT_SECURITY, "script-src 'self'");  // no CDN
        return handler.service(request, response);
    }
}
