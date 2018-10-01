package com.spp.cp.domain;

import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organizations")
public class Organization {

    private Long id;

    private Set<Company> subContractors;

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "org_subcontractors",
            joinColumns = { @JoinColumn(name = "org_id") },
            inverseJoinColumns = { @JoinColumn(name = "subcontractor_company_id") })
    public Set<Company> getSubContractors() {
        return subContractors;
    }

    public void setSubContractors(Set<Company> subContractors) {
        this.subContractors = subContractors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
