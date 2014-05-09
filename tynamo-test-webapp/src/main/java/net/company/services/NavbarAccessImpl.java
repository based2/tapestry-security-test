package net.company.services;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityService;
import org.tynamo.security.services.impl.SecurityFilterChain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service enabling and keeping the application navigation bar configuration
 * @date 18/04/14.
 * https://issues.apache.org/jira/browse/TAP5-2281
 */
public class NavbarAccessImpl implements NavbarAccess
{
   private final static Logger LOG = LoggerFactory.getLogger(AppModule.class);
   //@Inject
   //private Logger LOG;

    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_SELLER = "seller";
    public static final String ROLE_EDITOR = "editor";
    public static final String ROLE_ADMIN = "admin";

    public static final String T5_DASHBOARD = "T5Dashboard";
    public static final String MODE_DEV = "DEV$!|#@";
    public static final String MODE_STD = "STANDARD";

    private static String[][] MODES_PAGES_PATHS_PERMISSIONS_ICONS = new String[][]{
            {MODE_DEV, T5_DASHBOARD, null, null, "dashboard"}, // used only when isProduction = false: only in dev mode
            {MODE_STD,"Board", "/board/**", AppModule.PERMISSION_CUSTOMER, "glass"},
            {MODE_STD,"Stats", "/stats/**",  AppModule.PERMISSION_SELLER, "eye"},
            {MODE_STD,"Inventory", "/inventory/**",  AppModule.PERMISSION_EDITOR, "compass"},
            {MODE_DEV,"Controls", "/controls/**",  AppModule.PERMISSION_EDITOR, "flash"},
            {MODE_STD,"Admin", "/admin/**",  AppModule.PERMISSION_ADMIN, "gavel"},
            {MODE_DEV,"Bootswatch"}, // used only when isProduction = false, only in dev mode
            {MODE_STD,"About"},
            {MODE_STD,"Contact"},
            {MODE_STD,"Privacy"}
    };

    @Override
    public void setupSecurity(Configuration<SecurityFilterChain> configuration,
                                                       SecurityFilterChainFactory securityFactory, WebSecurityManager securityManager)
    {
        LOG.info("Log In page:" + AppModule.URL_LOGIN);
        configuration.add(securityFactory.createChain(AppModule.URL_LOGIN).add(securityFactory.anon()).build());
        LOG.info("Page after successful login:" + AppModule.URL_LOGIN);
        configuration.add(securityFactory.createChain(AppModule.URL_SUCCESS).add(securityFactory.user()).build());
        LOG.info("Redirect page for unauthorized access:" + AppModule.URL_UNAUTHORIZED);
        configuration.add(securityFactory.createChain(AppModule.URL_UNAUTHORIZED).add(securityFactory.user()).build());

        for (String[] modePagePathPermission : MODES_PAGES_PATHS_PERMISSIONS_ICONS) {
            if ((modePagePathPermission.length > 0) && (InternalUtils.isNonBlank(modePagePathPermission[0]))) {
                if (MODE_STD.equals(get(0, modePagePathPermission))) {
                    //enablePermissions(configuration, securityFactory, modePagePathPermission, "");
                    if (modePagePathPermission.length > 3) {
                        LOG.info(PRIVATE + " " + modePagePathPermission[1] + " > " + modePagePathPermission[2] + " > " + modePagePathPermission[3]);
                        configuration.add(securityFactory.createChain(modePagePathPermission[2]).add(securityFactory.perms(), modePagePathPermission[3]).build());
                    } else if (modePagePathPermission.length == 2) {
                        LOG.info(PUBLIC + " " + modePagePathPermission[1] + " > " + modePagePathPermission[1]);
                    } else  {
                        LOG.info(PUBLIC + " " + modePagePathPermission[1]);
                    }
                } else if (isModeDev(modePagePathPermission)) {
                    //enablePermissions(configuration, securityFactory, modePagePathPermission, DEVELOPMENT+" ");
                    if (modePagePathPermission.length > 3) {
                        LOG.info(DEVELOPMENT + " " + PRIVATE + " " + modePagePathPermission[1] + " > " + modePagePathPermission[2] + " > " + modePagePathPermission[3]);
                        configuration.add(securityFactory.createChain(modePagePathPermission[2]).add(securityFactory.perms(), modePagePathPermission[3]).build());
                    } else if (modePagePathPermission.length == 2) {
                        LOG.info(DEVELOPMENT + " " + PUBLIC + " " + modePagePathPermission[1] + " > " + modePagePathPermission[1]);
                    } else  {
                        LOG.info(DEVELOPMENT + " " + PUBLIC + " " + modePagePathPermission[1]);
                    }
                } else {
                    LOG.warn("Unknown page mode:"+ modePagePathPermission[0] + " for " + modePagePathPermission[1]);
                }
            }
        }
    }

    private String get(int i, String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[i];
        } catch (Exception e) {
            return "";
        }
    }
  /*
    private String getMode(String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[0];
        } catch (Exception e) {
            return "";
        }
    }*/

    private String getPage(String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[1];
        } catch (Exception e) {
            return "";
        }
    }
     /*
    private String getPath(String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[2];
        } catch (Exception e) {
            return "";
        }
    }  */

    private String getPermission(String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[3];
        } catch (Exception e) {
            return "";
        }
    }

    private String getIcon(String[] modePagePathPermission)
    {
        try {
            return modePagePathPermission[4];
        } catch (Exception e) {
            return "";
        }
    }

    // todo enable i18n with property file and T5 messages
    private final static String DEVELOPMENT = "Development";
    private final static String PRIVATE = "Private";
    private final static String PUBLIC = "Public";

    /*org.apache.catalina.core.StandardWrapperValve invoke
    GRAVE: Servlet.service() for servlet [default] in context with path [] threw exception
    java.lang.NullPointerException at org.apache.shiro.util.AntPathMatcher.doMatch(AntPathMatcher.java:109)
    private void enablePermissions(Configuration<SecurityFilterChain> configuration, SecurityFilterChainFactory securityFactory, 
                                   String[] modePagePathPermission, String dev)
    {
        if (modePagePathPermission.length > 3) {
            LOG.info(dev + PRIVATE + " " + modePagePathPermission[1] + " > " + modePagePathPermission[2] + " > " + modePagePathPermission[3]);
            configuration.add(securityFactory.createChain(modePagePathPermission[2]).add(securityFactory.perms(), modePagePathPermission[3]).build());
        } else if (modePagePathPermission.length == 2) {
            LOG.info(dev + PUBLIC + " " + modePagePathPermission[1] + " > " + modePagePathPermission[1]);
        } else  {
            LOG.info(dev + PUBLIC + " " + modePagePathPermission[1]);
        }
    }  */

    public Map<String, String> getPageNames(SecurityService securityService)
    {
       Map<String, String> pageIcons = new LinkedHashMap<String, String>();
       for (String[] pageOrDir : MODES_PAGES_PATHS_PERMISSIONS_ICONS) {
           if (pageOrDir != null) {
               try {
                   LOG.debug(getPage(pageOrDir)+"::"+getPermission(pageOrDir));
                  if (isModeDev(pageOrDir)) {
                      pageIcons.put(getPage(pageOrDir), getIcon(pageOrDir));
                  } else {
                      String permission = getPermission(pageOrDir);
                      if (InternalUtils.isBlank(permission)) {
                          pageIcons.put(getPage(pageOrDir), getIcon(pageOrDir));
                      }  else {
                          if (securityService.hasPermission(permission)) {
                              pageIcons.put(getPage(pageOrDir), getIcon(pageOrDir));
                          }
                      }
                  }
               } catch (Exception e) {
                   LOG.error("Failed to proceed to register pageOrDir " + getPage(pageOrDir)
                                   + " for permission " + getPermission(pageOrDir));
               }
           }
       }
       return pageIcons;
    }

    private boolean isModeDev(String[] pageOrDir)
    {
        if ((NavbarAccessImpl.MODE_DEV.equals(pageOrDir[0]))) {
            if (!AppModule.isProduction) return true;
        }
        return false;
    }

}
