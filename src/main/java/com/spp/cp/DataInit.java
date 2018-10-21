package com.spp.cp;

import com.spp.cp.db.*;
import com.spp.cp.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Initialize the database with testData.
 */
@Component
@ExcludeFromTest
public class DataInit implements ApplicationRunner {


    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrganizationRepository orgRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private FreightRepository freightRepo;

    private Long orderId;
    private Organization org1;
    private Organization org2;
    private Company fantastiko;
    private Company peevi;
    private Company dudovi;
    private User zaro;
    private User kita;
    private User stefan;

    private void createOrganizations() {
        Organization org1 = new Organization();
        org1.setName("Peevi");
        org1 = orgRepo.save(org1);
        this.org1 = org1;

        Organization org2 = new Organization();
        org2.setName("Dudovi");
        org2 = orgRepo.save(org2);

        this.org2 = org2;
    }

    private void createUsers() {

        PasswordEncoder pe = new BCryptPasswordEncoder(11);

        User zaro = new User();
        zaro.setUsername("zarrro");
        zaro.setPassword(pe.encode("zarrro"));
        zaro.setEmail("zaro@gmail.com");
        zaro.setOrg(org1);

        User kita = new User();
        kita.setEmail("kita@gmail.com");
        kita.setUsername("kita");
        kita.setPassword(pe.encode("kita"));
        kita.setOrg(org1);;

        User stefan = new User();
        stefan.setEmail("stefan@yahoo.com");
        stefan.setUsername("stefan");
        stefan.setPassword(pe.encode("stefan"));
        stefan.setOrg(org2);

        userRepo.save(zaro);
        userRepo.save(stefan);
        userRepo.save(kita);

        this.zaro = zaro;
        this.stefan = stefan;
        this.kita = kita;
    }

    private void createCompanies() {
        Company fantastiko = new Company();
        fantastiko.setBulstat("8139723198731");
        fantastiko.setName("Fantastiko");

        Company peeviCargo = new Company();
        peeviCargo.setBulstat("7766622227722");
        peeviCargo.setOwner(org1);
        peeviCargo.setName("peeviCargo");
        peeviCargo.setHauler(true);

        Company dudoviCargo = new Company();
        dudoviCargo.setBulstat("7766622227722");
        dudoviCargo.setOwner(org2);
        dudoviCargo.setName("CargoPay transport");
        dudoviCargo.setHauler(true);

        Set<Company> subContractor = new HashSet<>();

        companyRepo.save(fantastiko);
        companyRepo.save(peeviCargo);
        companyRepo.save(dudoviCargo);

        subContractor.add(dudoviCargo);
        org1.setSubContractors(subContractor);
        orgRepo.save(org1);

        this.fantastiko = fantastiko;
        peevi = peeviCargo;
        dudovi = dudoviCargo;
    }

    private void createOrders() {
        Order firstOrder = new Order();
        firstOrder.setCustomer(fantastiko);
        firstOrder.setContractor(peevi);
        firstOrder.setCreatedBy(zaro);
        firstOrder.setOrg(zaro.getOrg());
        firstOrder.setGoods("Cucumbers");
        firstOrder.setType(Order.Type.FTL);

        Date deadline = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(deadline);
        c2.add(Calendar.DATE, 14);
        deadline = c2.getTime();
        firstOrder.setDeadline(deadline);

        firstOrder = orderRepo.save(firstOrder);

        Freight freight1 = new Freight();
        freight1.setOrder(firstOrder);
        freight1.setLoadDate(new Date());
        freight1.setState(Freight.State.PENDING);

        Freight freight2 = new Freight();
        freight2.setOrder(firstOrder);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();


        //TODO: explore if LocalDate and LocalDateTime can be used as DB types
        freight2.setState(Freight.State.PENDING);
        freight2.setLoadDate(dt);

        List<Freight> freights = new ArrayList<>(2);
        freights.add(freight1);
        freights.add(freight2);
        freightRepo.saveAll(freights);

        orderId = firstOrder.getId();
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("<<<< Init some data >>>>");
        createOrganizations();
        createUsers();
        createCompanies();
        createOrders();
    }
}