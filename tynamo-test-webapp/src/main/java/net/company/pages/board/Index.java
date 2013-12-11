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

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

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

    public boolean isUnlimitedInstalled(){

        String ERROR_INSTALL_UNLIMITED = "Please install several files in your ${java.home - JDK/JRE and/or Java/JRE}/lib/security/ from\n"
                + "http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html\n"
                + "to enable Unlimited Strength Jurisdiction Policy.";

        try {
            int maxKeyLengthRC5 = Cipher.getMaxAllowedKeyLength("RC5");
            LOG.info("RC5 maxKeyLength:"+maxKeyLengthRC5);

            int maxKeyLengthAES = Cipher.getMaxAllowedKeyLength("AES");
            LOG.info("AES maxKeyLength:"+maxKeyLengthAES);

            int maxKeyLengthRSA = Cipher.getMaxAllowedKeyLength("RSA"); // Seems not to change?!
            LOG.info("RSA maxKeyLength:"+maxKeyLengthRSA);

            int maxKeyLengthDES = Cipher.getMaxAllowedKeyLength("DES");
            LOG.info("DES maxKeyLength:"+maxKeyLengthDES);

            if ((maxKeyLengthRC5>256)
               && (maxKeyLengthAES>128)
               && (maxKeyLengthDES>64)) {
                LOG.info("Unlimited JCE Security enabled :-), alt: Bouncy Castle http://www.bouncycastle.org/");
                return true;
            } else {
                LOG.error(ERROR_INSTALL_UNLIMITED);
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.error(ERROR_INSTALL_UNLIMITED, e);
        }
        return false;

        // note osx: http://stackoverflow.com/questions/18742733/badpaddingexception-after-porting-java-app-to-os-x
    }
}
