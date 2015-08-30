package fleet.logisure.chhavi.tflapp;

import java.io.Serializable;

/**
 * Created by pruthvi on 14/7/15.
 */
public class OrderItem implements Serializable {

    public String name;
    public String orderId;
    public String phoneNo;
    public String fromAddress;
    public String toAddress;
    public String status;
    public double qtyPickedUp;
    public double qtyDelivered;
    public String Cashcollectedfrom;

    public int temporaryQty;
    public String signatureName;
    public String photoName;
    public String vehicle;
    public String temporaryStatus;
    public double temporaryAmount = 0 ;
    public String CRN ;
    public boolean isFinish = false;
    public double enrouteExpensesCost;
    public String enrouteExpensesName;
}