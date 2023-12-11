import java.util.Scanner;

public class Order {
    int cargo;
    String destination;
    boolean canceled;

    public Order(int cargo,String destination) {
    this.cargo=cargo;
    this.destination=destination;
    this.canceled=false;

    }
}
