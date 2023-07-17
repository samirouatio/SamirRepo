package com.Battle_Simulation_Game.Speakerman.TitanTvMan.SkibidiToiletWar.MyClasses;

public class WalkthroughClass {

    private String titre;

    private String description;
    private String image;

    public WalkthroughClass(String titre, String description, String image) {
        this.titre = titre;
        this.description = description;
        this.image = image;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
