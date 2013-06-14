package net.company.pages.admin;

@org.apache.tapestry5.annotations.Secure
@org.apache.shiro.authz.annotation.RequiresPermissions(net.company.services.AppModule.PERMISSION_ADMIN)
public class Index
{

}
