package net.company.pages.admin;

import net.company.services.AppModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresPermissions(AppModule.PERMISSION_ADMIN)
public class Index
{
     // add JCE unlimited test
}
