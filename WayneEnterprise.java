import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WayneEnterprise {
    private final BlockingQueue<Order> ordersQueue=new LinkedBlockingQueue<>();
    private final BlockingQueue<Order> shippingQueue=new LinkedBlockingQueue<>();

    private int totalRevenue=0;
    private int totalDelivered=0;
    private int totalCanceled=0;
    private final Object lock=new Object();

    public void placeOrder(Order order){
        ordersQueue.offer(order);
    }

    public void executeOrder(){
        while (totalRevenue<1000000){
            try{
                Order order =ordersQueue.poll(1,TimeUnit.SECONDS);
                if(order!=null){
                    if(order.canceled){
                        synchronized(lock){
                            totalCanceled++;
                            totalRevenue-=250;
                        }
                    } else{
                        synchronized (lock){
                            totalDelivered++;
                            totalRevenue+=1000;
                        }
                    }
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void shipCargo(){
        while(totalRevenue<1000000){
            try{
                Order order=shippingQueue.poll(1, TimeUnit.SECONDS);
                if(order!=null && order.cargo>=50){
                    synchronized (lock){
                        totalRevenue+=1000;
                        totalDelivered++;
                    }
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public int getTotalDelivered(){
        return totalDelivered;
    }
    public int getTotalCanceled(){
        return totalCanceled;
    }
}
