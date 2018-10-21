package com.spp.cp.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    private Long id;

    private String bulstat;

    private String name;

    private boolean isHauler;

    private boolean isSpedition;

    private Organization owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBulstat() {
        return bulstat;
    }

    public void setBulstat(String bulstat) {
        this.bulstat = bulstat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "owner_org_id", nullable = true)
    public Organization getOwner() {
        return owner;
    }

    public void setOwner(Organization org) {
        this.owner = org;
    }

    public boolean isHauler() {
        return isHauler;
    }

    public void setHauler(boolean hauler) {
        isHauler = hauler;
    }

    public boolean isSpedition() {
        return isSpedition;
    }

    public void setSpedition(boolean spedition) {
        isSpedition = spedition;
    }
}
