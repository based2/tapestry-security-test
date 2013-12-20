package net.company.services;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

import java.io.IOException;

/**
 * Present the logout [Path/Page or full URI (https://site/logout)] to the browser or smart proxy
 * to allow preventive Auto-Logout functionality from the browser.
 *
 * Use cases:
 *  Logout when closing the browser windows that enabled login,
 *  Prevent two login-authenticated sessions at the same time on different sites by closing the previous authenticated session),
 *  Give the ability to the user to set his/her own session timeout,
 *  ...
 *
 * todo RFC
 *
 *  Send only the header after successful login answer => done in pages/Index#onActivate()
 * todo specific ResponseAutoLogoutHeader
 *
 * http://security.stackexchange.com/questions/43809/do-we-need-to-logout-of-webapps logout,netsec
 + http://popuplogout.iniqua.com/
 * https://addons.mozilla.org/en-US/firefox/addon/popup-logout/
 * https://addons.mozilla.org/en-US/firefox/addon/http-logout/
 * http://www.w3.org/community/sesmanbro/
 *
 */
public class AutoLogoutHeader implements RequestFilter
{
    private static final String AUTO_LOGOUT = "auto-logout";

    public boolean service(Request request, Response response, RequestHandler handler) throws IOException
    {
        response.setHeader(AUTO_LOGOUT, "logout"); // Path/Page or full URI (https://site/logout)
        return handler.service(request, response);
    }
}
