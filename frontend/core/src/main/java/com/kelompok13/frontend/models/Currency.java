package com.kelompok13.frontend.models;
//in game money for shop purchases or challenges enemy
public class Currency {
    private int money = 100;

    public Currency(){
        this.money = 100; //default money
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // used when player wins battle
    public void addMoney(int amount) {
        this.money += amount;
    }

    // used when shopping for joker card or item in shop or when challenging enemy
    public void deductMoney(int amount) {
        if(this.money >= amount){
            this.money -= amount;
        } else {
            System.out.println("Not enough money!");
        }
    }

}
