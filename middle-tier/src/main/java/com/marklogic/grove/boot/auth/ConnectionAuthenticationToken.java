/*
 * Copyright 2012-2019 MarkLogic Corporation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.marklogic.grove.boot.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

public class ConnectionAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;
    private Object hostname;
    private int projectId;
    private String environment;

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>ConnectionAuthenticationToken</code>, as the {@link #isAuthenticated()}
     * will return <code>false</code>.
     *
     */
    public ConnectionAuthenticationToken(Object principal, Object credentials, Object hostname, int projectId, String environment) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.hostname = hostname;
        this.projectId = projectId;
        this.environment = environment;
        setAuthenticated(false);
    }

    public ConnectionAuthenticationToken(Object principal, Object credentials, Object hostname, int projectId, String environment,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.hostname = hostname;
        this.projectId = projectId;
        this.environment = environment;
        super.setAuthenticated(true); // must use super, as we override
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public Object getHostname() {
        return this.hostname;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        credentials = null;
    }
}
