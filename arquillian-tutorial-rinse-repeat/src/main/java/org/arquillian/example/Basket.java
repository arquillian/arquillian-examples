package org.arquillian.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> items;
    
    @EJB
    private OrderRepository repo;
    
    public void addItem(String item) {
        items.add(item);
    }
    
    public List<String> getItems() {
        return Collections.unmodifiableList(items);
    }
    
    public int getItemCount() {
        return items.size();
    }
    
    public void placeOrder() {
        repo.addOrder(items);
        items.clear();
    }
    
    @PostConstruct
    void initialize() {
        items = new ArrayList<String>();
    }
}
