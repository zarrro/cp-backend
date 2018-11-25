package com.spp.cp.domain;

import com.spp.cp.db.FreightRepository;
import com.spp.cp.db.OrderRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class EntityManagerBase {

    @Autowired
    protected FreightRepository freightRepo;

    protected <W> W loadById(CrudRepository<W, Long> repo, Long id) {
        if(id == null)
            throw new Exceptions.ArgumentNotSetException("id");

        Optional<W> res = repo.findById(id);

        if(!res.isPresent())
            throw new Exceptions.EntityNotFoundException(res.get().getClass().getSimpleName() + " with id: " + id);
        return res.get();
    }

}
