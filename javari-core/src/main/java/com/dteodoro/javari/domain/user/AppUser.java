package com.dteodoro.javari.domain.user;

import java.util.UUID;

public interface AppUser {
    public UUID getId();
    public String getUsername();
    public BaseUser create(BaseUser user);
}
