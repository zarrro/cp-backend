package com.spp.cp;

import com.spp.cp.db.CompanyRepository;
import com.spp.cp.db.OrderRepository;
import com.spp.cp.db.OrganizationRepository;
import com.spp.cp.db.UserRepository;
import com.spp.cp.domain.Company;
import com.spp.cp.domain.Order;
import com.spp.cp.domain.Organization;
import com.spp.cp.domain.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrganizationRepository orgRepo;

    @Autowired
    private CompanyRepository companyRepo;

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
        assertEquals(1, orgRepo.count());
        assertNotNull(org1.getId());
        this.org1 = org1;

        Organization org2 = new Organization();
        org2.setName("Dudovi");
        org2 = orgRepo.save(org2);
        assertEquals(2, orgRepo.count());
        assertNotNull(org2.getId());
        this.org2 = org2;
    }

    private void createUsers() {
        User zaro = new User();
        zaro.setEmail("zaro@gmail.com");
        zaro.setOrg(org1);

        User kita = new User();
        kita.setEmail("kita@gmail.com");
        kita.setOrg(org1);;

        User stefan = new User();
        stefan.setEmail("stefan@yahoo.com");
        stefan.setOrg(org2);

        userRepo.save(zaro);
        userRepo.save(stefan);
        userRepo.save(kita);

        assertEquals(3, userRepo.count());
        assertNotNull(zaro.getId());
        assertNotNull(kita.getId());
        assertNotNull(stefan.getId());

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

        assertEquals(3, companyRepo.count());
        assertNotNull(fantastiko);
        assertNotNull(peeviCargo);
        assertNotNull(dudoviCargo);


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
        firstOrder.setPrice(10050);

        assertNull(firstOrder.getId());

        firstOrder = orderRepo.save(firstOrder);

        assertNotNull(firstOrder.getId());
        orderId = firstOrder.getId();
    }

    @Before
    public void setUp() {
        createOrganizations();
        createUsers();
        createCompanies();
        createOrders();
    }


    @Test
    public void testFetchOrder() {
        //test fetch by order id
        Order order = orderRepo.findById(orderId).get();

        assertNotNull(order.getCreatedBy());
        assertNotNull(order.getCreatedBy().getId());
        assertEquals(zaro.getId(), order.getCreatedBy().getId());

        assertNotNull(order.getCustomer());
        assertNotNull(order.getCustomer().getId());
        assertEquals(fantastiko.getId(), order.getCustomer().getId());

        assertNotNull(order.getContractor());
        assertNotNull(order.getContractor().getId());
        assertEquals(peevi.getId(), order.getContractor().getId());

        // test fetch by orgId
        List<Order> orders = orderRepo.findByOrgId(zaro.getOrg().getId());
        assertEquals(1, orders.size());
        assertEquals(orders.get(0).getId(), orderId);

        testFetchOrganization();
    }

    public void testFetchOrganization() {
        // verify dudovi is fetched as subcontractor
        Organization org1mirror = orgRepo.findById(org1.getId()).get();
        Set<Company> org1subContr = org1mirror.getSubContractors();
        assertNotNull(org1subContr);
        assertEquals(1, org1subContr.size());
        for(Company subContr : org1subContr) {
            assertEquals(subContr.getId(), dudovi.getId());
        }
    }

    @After
    public void tearDown() {

        orderRepo.deleteById(orderId);

        assertFalse(orderRepo.findById(orderId).isPresent());

        // delete operation should not be cascaded so user should be still present
        assertTrue(userRepo.findById(zaro.getId()).isPresent());

    }
}
