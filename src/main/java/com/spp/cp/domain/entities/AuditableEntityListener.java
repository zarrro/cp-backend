package com.spp.cp.domain.entities;

import com.spp.cp.config.MyUserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class AuditableEntityListener {

    @PrePersist
    public void prePersist(Auditable target) {
        UserEntity user = getCurrentUser();
        target.setCreatedBy(user);
        target.setOrg(user.getOrg());

        target.setCreationDate(new Date());
    }

    @PreUpdate
    public void preUpdate(Auditable target) {
        Assert.isTrue(target.getCreatedBy().getOrg().equals(getCurrentUser().getOrg()),
                "updater must be of the same org as the creator");
        target.setLastModifiedBy(getCurrentUser());
        target.setLastModifiedDate(new Date());

    }

    @PostLoad
    public void verifyOwnership(Auditable target) {
        if(!target.getOrg().equals(getCurrentUser().getOrg())) {
            throw new SecurityException("user is not in the same org as the entity");
        }
    }

    private static UserEntity getCurrentUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Assert.notNull(auth, "for auditable entity, authentication must not be null");

        // other principal types are not expected, refactor if needed in the future
        Assert.isInstanceOf(MyUserPrincipal.class, auth.getPrincipal());
        return ((MyUserPrincipal) auth.getPrincipal()).getUser();
    }
}
