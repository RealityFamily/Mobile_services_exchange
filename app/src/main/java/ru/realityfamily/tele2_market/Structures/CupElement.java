package ru.realityfamily.tele2_market.Structures;

public class CupElement {
    public CupElement() {}

    public CupElement(Integer Price, Integer BuyOrders, Integer SellOrders) {
        price = Price;
        buy_orders = BuyOrders;
        sell_orders = SellOrders;
    }

    Integer price;
    Integer buy_orders;
    Integer sell_orders;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBuy_orders() {
        return buy_orders;
    }

    public void setBuy_orders(Integer buy_orders) {
        this.buy_orders = buy_orders;
    }

    public Integer getSell_orders() {
        return sell_orders;
    }

    public void setSell_orders(Integer sell_orders) {
        this.sell_orders = sell_orders;
    }
}
