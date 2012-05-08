package org.arquillian.example;

import java.util.List;
import javax.ejb.Local;

@Local
public interface OrderRepository {
    void addOrder(List<String> order);
    List<List<String>> getOrders();
    int getOrderCount();
}