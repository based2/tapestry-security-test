package net.company.pages.junior;

import net.company.services.AppModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresPermissions(AppModule.PERMISSION_JUNIOR)
public class Index
{

}
