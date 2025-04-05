/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * A wrapper class that encapsulates a list of Order objects for XML serialization. It ensures that multiple orders are grouped under a custom root element (<orders>), making the XML output more structured and readable.
 */
package com.reuveny.Electronics.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.reuveny.Electronics.model.Order;

import java.util.List;

@JacksonXmlRootElement(localName = "orders")
public class OrderListWrapper {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "order")
    private List<Order> orders;

    public OrderListWrapper() {
    }

    public OrderListWrapper(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
