package net.company.pages.stats;


import net.company.services.AppModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Secure;

@Secure
@RequiresPermissions(AppModule.PERMISSION_SELLER)
public class Index {
}
