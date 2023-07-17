package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses;

public class MenuItems {

    private String name;
    private String ico;
    private String bg;

    public MenuItems() {}

    public MenuItems(String name, String ico, String bg) {
        this.name = name;
        this.ico = ico;
        this.bg = bg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

}
