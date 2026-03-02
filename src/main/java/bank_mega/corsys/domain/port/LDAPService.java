package bank_mega.corsys.domain.port;

import bank_mega.corsys.domain.model.ldap.LDAPResponse;

public interface LDAPService {

    /**
     * Verify user password against LDAP
     *
     * @param nip  user identifier
     * @param pass plain password
     * @return LDAPResponse containing verification result
     */
    LDAPResponse verifyPassword(String nip, String pass);
}
