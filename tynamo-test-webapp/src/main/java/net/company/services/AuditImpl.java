package net.company.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * Audit Service implementation
 *   with log debug
 *
 *   todo cache
 */
public class AuditImpl implements Audit {
    @Inject
    private Logger LOG;

    public void create(String who, String service, String what){
        LOG.debug("%s %s %s", who, service, what);
    }
}
