package com.spp.cp;

import com.spp.cp.config.MyUserPrincipal;
import com.spp.cp.db.*;
import com.spp.cp.domain.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // 50 random words
    private static final String[] WORDS = ("songs,ticket,wire,mark,pigs,sleep,brake,spoon,night,fog,suit,icicle,cough," +
            "title,umbrella,cake,account,taste,frogs,dog,brother,story,window,country," +
            "oranges,week,pipe,pot,quartz,pies,soap,ray,servant,sky,veil,language,glove,toothbrush," +
            "screw,rabbit,yard,history,curtain,spark,tank,juice,kittens,governor,voyage,quince").split(",");
    private static Random rand = new Random(System.currentTimeMillis());

    private List<Company> customers = new ArrayList<>();

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

    @Autowired
    private CityRepository cityRepo;

    @Autowired
    private AddressRepository addressRepo;

    @Autowired
    private ItineraryRepository itinereryRepo;

    private Long orderId;
    private Organization org1;
    private Organization org2;
    private Company fantastiko;
    private Company peevi;
    private Company dudovi;
    private UserEntity zaro;
    private UserEntity kita;
    private UserEntity stefan;

    private int itenaryCounter = 0;

    private static String getRandomGood() {
        String good = WORDS[rand.nextInt(WORDS.length)];
        int quantity = rand.nextInt(10) + 1;
        return String.format("%s X %s", quantity, good);
    }

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

        UserEntity zaro = new UserEntity();
        zaro.setUsername("zarrro");
        zaro.setPassword(pe.encode("zarrro"));
        zaro.setEmail("zaro@gmail.com");
        zaro.setOrg(org1);

        UserEntity kita = new UserEntity();
        kita.setEmail("kita@gmail.com");
        kita.setUsername("kita");
        kita.setPassword(pe.encode("kita"));
        kita.setOrg(org1);
        ;

        UserEntity stefan = new UserEntity();
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

        Authentication auth = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return new MyUserPrincipal(zaro);
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        };

        //for testing purposes, so that auditing doesn't fail
        SecurityContextHolder.getContext().setAuthentication(auth);

        Company fantastiko = new Company();
        fantastiko.setBulstat("8139723198731");
        fantastiko.setName("Fantastiko");
        customers.add(fantastiko);

        Company billa = new Company();
        billa.setBulstat("856433198731");
        billa.setName("Billa");
        customers.add(billa);

        Company lidl = new Company();
        lidl.setBulstat("9502745823434");
        lidl.setName("Lidl");
        customers.add(lidl);

        Company raffi = new Company();
        raffi.setBulstat("11139723193242");
        raffi.setName("raffi");
        customers.add(raffi);

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

        for (Company cust : customers) {
            companyRepo.save(cust);
        }
        companyRepo.save(peeviCargo);
        companyRepo.save(dudoviCargo);

        subContractor.add(dudoviCargo);
        org1.setSubContractors(subContractor);
        orgRepo.save(org1);

        this.fantastiko = fantastiko;
        peevi = peeviCargo;
        dudovi = dudoviCargo;
    }

    private void createCountriesAndCities() {
        Country bg = createCountry("Bulgaria", "BG");
        Country gr = createCountry("Greece", "GR");
        Country de = createCountry("Germany", "DE");

        createCityRecordWithAddresses("Sofia", bg);
        createCityRecordWithAddresses("Varna", bg);
        createCityRecordWithAddresses("Burgas", bg);
        createCityRecordWithAddresses("Ruse", bg);

        createCityRecordWithAddresses("Thessaloniki", gr);
        createCityRecordWithAddresses("Athens", gr);
        createCityRecordWithAddresses("Larissa", gr);
        createCityRecordWithAddresses("Serres", gr);

        createCityRecordWithAddresses("Berlin", de);
        createCityRecordWithAddresses("Cologne", de);
        createCityRecordWithAddresses("Munich", de);
        createCityRecordWithAddresses("Frankfurt", de);

    }

    private void createItineraries() {
        Iterable<Address> addresses = addressRepo.findAll();

        int pointCount = rand.nextInt(4) + 1;
        List<ItineraryPoint> itinPoints = new ArrayList<>(pointCount);
        for (Address addr : addresses) {
            ItineraryPoint ip = new ItineraryPoint();
            ip.setAddress(addr);
            ip.setPointOrder(pointCount);
            itinPoints.add(ip);
            pointCount--;
            if (pointCount == 0) {
                Itinerary itin = new Itinerary();
                itin.setPoints(itinPoints);
                itinereryRepo.save(itin);
                pointCount = rand.nextInt(4) + 1;
                itinPoints = new ArrayList<>(pointCount);
            }
        }
    }

    private Country createCountry(String name, String countryCode) {
        Country cou = new Country();
        cou.setCode(countryCode);
        cou.setName(name);
        return cou;
    }

    private void createCityRecordWithAddresses(String name, Country countryCode) {
        City c = new City();
        c.setName(name);
        c.setCountry(countryCode);
        c = cityRepo.save(c);

        for (int i = 1; i <= 3; i++) {
            Address addr = new Address();
            addr.setAddressLine("Address " + i + " of city " + c.getName());
            addr.setCity(c);
            addr = addressRepo.save(addr);
        }
    }

    private ItineraryPoint createItenaryPoint(Address a) {
        ItineraryPoint ip = new ItineraryPoint();
        ip.setAddress(a);
        ip.setPointOrder(itenaryCounter++);
        return ip;
    }


    private Company getRandomCompany() {
        return customers.get(rand.nextInt(customers.size()));
    }

    private void createOrders(Itinerary itinerary) {
        Order firstOrder = new Order();
        firstOrder.setCustomer(getRandomCompany());
        firstOrder.setContractor(peevi);
        firstOrder.setGoods(getRandomGood());
        firstOrder.setType(Order.Type.FTL);

        firstOrder.setItenary(itinerary);

        Date deadline = new Date();
        Calendar c2 = Calendar.getInstance();
        c2.setTime(deadline);
        c2.add(Calendar.DATE, rand.nextInt(25) + 5);
        deadline = c2.getTime();
        firstOrder.setDeadline(deadline);
        firstOrder.setState(Order.State.OPEN);

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
        createCountriesAndCities();
        createItineraries();

        Iterator<Itinerary> itit = itinereryRepo.findAll().iterator();
        Itinerary it = null;
        // create 20 orders
        for (int i = 0; i < 20; i++) {
            if (itit.hasNext()) {
                it = itit.next();
            }
            createOrders(it);
        }
    }
}