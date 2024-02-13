package com.github.paopaoyue.onmyojimod.option;

import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;

public class Hardcore extends TopPanelItem {

    public static final String ID = "Onmyoji:Hardcore";
    private static final Texture IMG = new Texture("yourmodresources/images/icon.png");

    public Hardcore() {
        super(IMG, ID);
    }

    @Override
    protected void onClick() {
        // your onclick code
    }
}
