package com.spp.cp.domain;

import com.spp.cp.db.AddressRepository;
import com.spp.cp.db.CompanyRepository;
import com.spp.cp.db.ItineraryRepository;
import com.spp.cp.db.OrderRepository;
import com.spp.cp.domain.entities.*;
import com.spp.cp.rest.CreateFreightParams;
import com.spp.cp.rest.CreateOrderParams;
import com.spp.cp.rest.UpdateOrderParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl extends EntityManagerBase implements OrderService {

    @Autowired
    protected OrderRepository orderRepo;


    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private ItineraryRepository itineraryRepo;

    @Autowired
    private AddressRepository addressRepo;

    private static Freight createFreightInstance(CreateFreightParams createParams, Order order) {
        //TODO: implement
        return null;
    }

    @Override
    public Order createOrder(CreateOrderParams params) {

        validateArgumentIsSet(params.getGoods(), "orderParams.goods");
        validateArgumentIsSet(params.getDeadline(), "orderParams.deadline");

        Order order = new Order();
        order.setState(Order.State.OPEN);
        order.setCustomer(loadById(companyRepo, params.getCustomerCompanyId()));
        order.setContractor(loadById(companyRepo, params.getContractorCompanyId()));
        order.setDeadline(params.getDeadline());
        order.setGoods(params.getGoods());
        order.setType((params.getType() != null) ? params.getType() : Order.Type.FTL);
        order.setItenary(createItinerary(params.getAddresses()));

        return orderRepo.save(order);
    }

    @Override
    public Order updateOrder(Long orderId, UpdateOrderParams updateParams) {
        Order order = loadById(orderRepo, orderId);
        validateOpenState(order);
        updateOrder(order, updateParams);
        return orderRepo.save(order);
    }

    private void validateArgumentIsSet(Object o, String argName) {
        if (o == null)
            throw new Exceptions.ArgumentNotSetException(argName);
        if ((o instanceof String) && (((String) o).isEmpty()))
            throw new Exceptions.ArgumentNotSetException(argName);
    }


    private Itinerary createItinerary(List<Address> addresses) {

        List<Address> freshAddresses = saveOrLoadAddresses(addresses);

        List<ItineraryPoint> points = new ArrayList<>(freshAddresses.size());
        int i = 0;
        for (Address a : freshAddresses) {
            ItineraryPoint ip = new ItineraryPoint();
            ip.setAddress(a);
            ip.setPointOrder(i++);
            points.add(ip);
        }

        Itinerary it = new Itinerary();
        it.setPoints(points);
        itineraryRepo.save(it);
        return it;
    }

    private List<Address> saveOrLoadAddresses(List<Address> addresses) {
        List<Address> ret = new ArrayList<>(addresses.size());
        for (Address a : addresses) {
            if (a.getId() != null) {
                ret.add(loadById(addressRepo, a.getId()));
            } else {
                validateAddress(a);
                ret.add(addressRepo.save(a));
            }
        }
        return ret;
    }

    private void validateAddress(Address a) {
        if (a == null)
            throw new Exceptions.ArgumentNotSetException("CreateOrderParams:address");
        if (a.getId() == null) {
            // new address will be stored, so validate params
            if (a.getCity() == null)
                throw new IllegalArgumentException("city for address is null");
            if (a.getStreet() == null || a.getStreet().isEmpty())
                throw new IllegalArgumentException("street for address is not set");
            if ((a.getAddressLine() == null) && (a.getStreetNumber() == null) && (a.getZipCode() == null))
                throw new IllegalArgumentException("at least one of addressLine, zipCode or streetNumber must be set");
        }
    }

    private void validateOpenState(Order o) {
        if (!Order.State.OPEN.equals(o.getState()))
            throw new Exceptions.IllegalOrderStateException();
    }

    private void updateOrder(Order order, UpdateOrderParams updateParams) {
        if (updateParams == null)
            throw new Exceptions.ArgumentNotSetException("updateParams");

        if (updateParams.getDeadline() != null) {
            //TODO: add log
            order.setDeadline(updateParams.getDeadline());
        }

        if (updateParams.getGoods() != null) {
            //TODO: add log
            order.setGoods(updateParams.getGoods());
        }

        if (updateParams.getType() != null) {
            //TODO: add log
            order.setType(updateParams.getType());
        }

        if (updateParams.getState() != null) {
            if (order.getState().isValidTransition(updateParams.getState())) {
                order.setState(updateParams.getState());
            } else {
                throw new Exceptions.IllegalOrderStateTransitionException();
            }
        }
    }
}
