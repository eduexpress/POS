package lk.ijse.pos.db;

import lk.ijse.pos.model.Customer;
import lk.ijse.pos.model.Item;

import java.util.ArrayList;

public class DataBase {
    public static ArrayList<Customer> customersList = new ArrayList/*<>*/();
    public static ArrayList<Item>
            itemList =
            new ArrayList/*<>*/();

    static {
        customersList.add(new Customer("C-001", "Nimal",
                "Colombo", 25000));
        customersList.add(new Customer("C-002", "Wasantha",
                "Panadura", 50000));
    }

}
