package com.spp.cp.domain.entities;

import javax.persistence.*;
import java.util.Objects;
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

    /**
     * subContractors is not included in Organization's equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
