package com.dteodoro.javari.core.domain;

import java.util.UUID;

public interface AppUser {
    UUID getId();
    String getUsername();
    BaseUser create(BaseUser user);

}
