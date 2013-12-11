package net.company.services;

/**
 * Audit Service contract
 */
public interface Audit {
    void create(String who, String service, String what);
}
