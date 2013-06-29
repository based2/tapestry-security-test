package net.company.pages.senior;

import net.company.services.AppModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresPermissions(AppModule.PERMISSION_SENIOR)
public class Index
{

}
