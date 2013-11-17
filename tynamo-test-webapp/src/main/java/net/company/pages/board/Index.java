package net.company.pages.board;


import net.company.services.AppModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.internal.services.LoginContextService;

@Secure
@RequiresPermissions(AppModule.PERMISSION_CUSTOMER)
public class Index {

    private final static Logger LOG = LoggerFactory.getLogger(Index.class);

    @Inject
    private Logger logger;

    @Inject
    private LoginContextService loginContextService;

    @Inject
    private Request request;

    public String getUserLogin() {
        logger.debug("Authenticated: "+ SecurityUtils.getSubject().isAuthenticated() +
                " , name: "	+ SecurityUtils.getSubject().getPrincipal());
        return (String) SecurityUtils.getSubject().getPrincipal();
    }
}
