package lk.ijse.pos.views.tm;

public class OrderTM {
    private String id;
    private String date;
    private String customer;
    private double total;

    public OrderTM() {
    }

    public OrderTM(String id, String date, String customer, double total) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
