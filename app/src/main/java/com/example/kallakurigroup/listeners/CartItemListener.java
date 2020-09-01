package com.example.kallakurigroup.listeners;

public interface CartItemListener {

    void quantityCountChanges(int position, int selectedQty, float selectedPrice, float prodPrice, String type);

   // void quantityChanged();
}
