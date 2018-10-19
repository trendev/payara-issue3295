/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.mysecuredrestapi.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author jsie
 */
//@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    private final Map<String, Set<String>> accounts = new HashMap<>();
    private final Map<String, String> passwords = new TreeMap<>();

    @PostConstruct
    public void init() {
        accounts.put("hank-moody@californication.us",
                new HashSet<>(Arrays.asList("Special")));
        passwords.put("hank-moody@californication.us", "PASSWORD");
    }

    @Override
    public int priority() {
        return 10;
    }

    public CredentialValidationResult validate(
            UsernamePasswordCredential credential) {

        String user = credential.getCaller();

        if (credential.compareTo(user,
                passwords.get(user))) {
            return new CredentialValidationResult(user, accounts.get(user));
        }
        return INVALID_RESULT;
    }
}
