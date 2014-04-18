package net.company.pages.board;

import net.company.services.AppModule;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.lazan.t5.stitch.model.MapPropertyBeanModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.internal.services.LoginContextService;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Secure
@RequiresPermissions(AppModule.PERMISSION_CUSTOMER)
public class Index {

    private final static Logger LOG = LoggerFactory.getLogger(Index.class);

    @Property
    @Persist
    private Map[] suiteproviders;

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

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    private static MapPropertyBeanModelFactory providersFactory;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final static ImmutablePair<String, Class>[] PROVIDERS_PAIR = new ImmutablePair[]{
            new ImmutablePair("Name", String.class),
            new ImmutablePair("Key", String.class),
            new ImmutablePair("Value", String.class)
    };

    @SetupRender
    public void init()
    {
        this.loadProviders();
    }


    private void initProvidersFactory(){
        if (providersFactory==null) {
            providersFactory = new MapPropertyBeanModelFactory(PROVIDERS_PAIR);
        }
    }

    private void loadProviders() {
        if (suiteproviders==null) {
            initProvidersFactory();
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            for (Provider provider : Security.getProviders()) {
                for (String key : provider.stringPropertyNames())
                    maps.add(createProviderRecord(provider.getName(), key, provider.getProperty(key)));
            }
            @SuppressWarnings({"rawtypes", "unchecked"})
            Map<String, Object>[] array = (Map<String, Object>[]) new HashMap[maps.size()];
            suiteproviders=maps.toArray(array);
        }
    }

    private Map<String, Object> createProviderRecord(String name, String key, String value) {
        return providersFactory.createRecord(name, key, value);
       /* Map<String, Object> providerRecord = new HashMap<String, Object>();
        providerRecord.put("Name", name);
        providerRecord.put("Key", key);
        providerRecord.put("Value", value);
        return providerRecord; */
    }

    public BeanModel<Object> getProviderModel() {
        return providersFactory.createMapPropertyConduit(beanModelSource, messages);
       /* // Initially construct a BeanModel for object (no properties)
        BeanModel<Object> beanModel = beanModelSource.createDisplayModel(Object.class, messages);

        // add MapPropertyConduits for each map entry
        beanModel.add("Name", new MapPropertyConduit("Name", String.class));
        beanModel.add("Key", new MapPropertyConduit("Key", String.class));
        beanModel.add("Value", new MapPropertyConduit("Value", String.class));

        return beanModel; */
    }

    private static MapPropertyBeanModelFactory ciphersFactory;
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final static ImmutablePair<String, Class>[] CIPHERS_PAIR = new ImmutablePair[]{
            new ImmutablePair("Name", String.class),
            new ImmutablePair("Length", String.class)
    };

    private void initCiphersFactory(){
        if (ciphersFactory==null) {
            ciphersFactory = new MapPropertyBeanModelFactory(CIPHERS_PAIR);
        }
    }

    public Map[] getCiphers() {
        initCiphersFactory();
        return new Map[] {
           // for a basic test: Unlimited Strength Jurisdiction Policy
           createCipherRecord("AES"),
           createCipherRecord("RSA"),
           createCipherRecord("DES"),

           // http://stackoverflow.com/questions/2238135/good-list-of-weak-cipher-suites-for-java
           createCipherRecord("TLS_DHE_RSA_WITH_AES_128_CBC_SHA"),
           createCipherRecord("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA"),
           createCipherRecord("TLS_RSA_WITH_AES_128_CBC_SHA"),
           createCipherRecord("SSL_RSA_WITH_3DES_EDE_CBC_SHA"),

           // If you have a DSA certificate, the list would look like this:
           createCipherRecord("TLS_DHE_DSS_WITH_AES_128_CBC_SHA"),
           createCipherRecord("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA"),

           // https://wiki.mozilla.org/Security/Server_Side_TLS - openssl
           createCipherRecord("ECDHE-RSA-AES128-GCM-SHA256"),
           createCipherRecord("ECDHE-ECDSA-AES128-GCM-SHA256"),
           createCipherRecord("ECDHE-RSA-AES256-GCM-SHA384"),
           createCipherRecord("!OMEGA")
        };
    }

    private Map<String, Object> createCipherRecord(String name) {
        //Map<String, Object> cipherRecord = new HashMap<String, Object>();
        //cipherRecord.put("Name", name);
        try {
            //cipherRecord.put("Length", Cipher.getMaxAllowedKeyLength(name));
            return ciphersFactory.createRecord(name, Cipher.getMaxAllowedKeyLength(name));
        } catch (NoSuchAlgorithmException e) {
            //cipherRecord.put("Length", 0);
            return ciphersFactory.createRecord(name, 0);
        }
        //return cipherRecord;
    }

    public BeanModel<Object> getCipherModel() {
        return ciphersFactory.createMapPropertyConduit(beanModelSource, messages);
        /*// initially construct a BeanModel for object (no properties)
        BeanModel<Object> beanModel = beanModelSource.createDisplayModel(Object.class, messages);

        // add MapPropertyConduits for each map entry
        beanModel.add("Name", new MapPropertyConduit("Name", String.class));
        beanModel.add("Length", new MapPropertyConduit("Length", int.class));

        return beanModel;*/
    }

    public boolean isUnlimitedInstalled(){

        String ERROR_INSTALL_UNLIMITED = "Please install several files in your ${java.home - JDK/JRE and/or Java/JRE}/lib/security/ from\n"
                + "http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html\n"
                + "to enable Unlimited Strength Jurisdiction Policy.";

        try {
            int maxKeyLengthAES = Cipher.getMaxAllowedKeyLength("AES");
            LOG.info("AES maxKeyLength:" + maxKeyLengthAES);

            int maxKeyLengthRSA = Cipher.getMaxAllowedKeyLength("RSA"); // Seems not to change?!
            LOG.info("RSA maxKeyLength:" + maxKeyLengthRSA);

            int maxKeyLengthDES = Cipher.getMaxAllowedKeyLength("DES");
            LOG.info("DES maxKeyLength:" + maxKeyLengthDES);

            if ((maxKeyLengthAES>128) && (maxKeyLengthDES>64)) {
                LOG.info("Unlimited JCE Security enabled :-), alt: Bouncy Castle http://www.bouncycastle.org/");
                return true;
            } else {
                LOG.error(ERROR_INSTALL_UNLIMITED);
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.error(ERROR_INSTALL_UNLIMITED, e);
        }
        return false;

        // Note osX: http://stackoverflow.com/questions/18742733/badpaddingexception-after-porting-java-app-to-os-x
    }
}
