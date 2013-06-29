package net.company.pages.inventory;

import net.company.services.AppModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresPermissions(AppModule.PERMISSION_EDITOR)
public class Index {
}
