package com.spp.cp.domain.entities;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
@EntityListeners(AuditableEntityListener.class)
public abstract class Auditable  {

    private Organization org;

    protected UserEntity createdBy;


    protected Date creationDate;


    protected UserEntity lastModifiedBy;


    protected Date lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "org_id")
    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    @CreatedBy
    @ManyToOne
    @JoinColumn(name="created_by")
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @CreatedDate
    @Temporal(TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name="last_modified_by")
    public UserEntity getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UserEntity lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}