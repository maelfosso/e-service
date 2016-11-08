package org.cpc.yaounde.eservice.data;

import android.support.multidex.MultiDexApplication;

import org.cpc.yaounde.eservice.model.Exam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maelfosso on 8/26/16.
 */
public class GlobalVariable extends MultiDexApplication {

    private List<Exam> cart = new ArrayList<>();

    public void addCart(Exam model) {
        cart.add(model);
    }

    public void removeCart(Exam model) {
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getName().equals(model.getName())){
                cart.remove(i);
                break;
            }
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public List<Exam> getCart() {
        return cart;
    }

    public long getCartPriceTotal() {
        long total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total = total + cart.get(i).getPrice();
        }
        return total;
    }

    public long getCartItemTotal() {
        long total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total = total + 1; // cart.get(i).getPrice();
        }
        return total;
    }

    public int getCartItem() {
        return cart.size();
    }

    public void updateItemTotal(Exam model) {
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getName().equals(model.getName())){
                cart.remove(i);
                cart.add(i, model);
                break;
            }
        }
    }

    public boolean isCartExist(Exam model){
        for (int i = 0; i < cart.size(); i++) {
            if(cart.get(i).getName().equals(model.getName())){
                return true;
            }
        }
        return false;
    }
    
}
