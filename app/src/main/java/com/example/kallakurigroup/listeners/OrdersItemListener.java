package com.example.kallakurigroup.listeners;

import com.example.kallakurigroup.models.productsmodels.OrderDetails;

public interface OrdersItemListener {
  void trackOrder(OrderDetails orderDetails);
}
