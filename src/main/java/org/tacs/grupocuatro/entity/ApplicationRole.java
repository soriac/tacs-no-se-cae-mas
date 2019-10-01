package org.tacs.grupocuatro.entity;

import io.javalin.core.security.Role;

public enum ApplicationRole implements Role {
    ADMIN,
    USER,
    ANONYMOUS
}
