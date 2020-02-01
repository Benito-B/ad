package controller;

import model.Order;
import model.OrderLine;

/**
 * -GHibernate-
 *
 * @author Benito-B - 01/02/2020
 */
public class Utils {

    public static boolean isAdminOnly(Class c){
        if(c.equals(Order.class) || c.equals(OrderLine.class))
            return true;
        return false;
    }
}
