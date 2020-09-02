package com.example.kallakurigroup.listeners;

public interface ProductItemListener {

    void productSelected(int position);

    void quantityCountChanges(int position, int selectedQty, float selectedPrice, float prodPrice, String type);
}
