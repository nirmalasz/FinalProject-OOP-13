package com.kelompok13.frontend.background;

public class BackgroundFactory {

    // path to background images
    private static final String PATH_START = "background/start.png";
    private static final String PATH_MENU  = "background/menu.png";
    private static final String PATH_MAIN  = "background/main_tile.jpg";
    private static final String PATH_BATTLE = "background/battle.png";
    private static final String PATH_DIALOGUE = "background/dialogue.png";
    private static final String PATH_SHOP  = "background/shop.png";
    private static final String PATH_ENDING = "background/ending.png";

    public static Background create(BackgroundType type) {
        switch (type) {
            case START:
                return new Background(PATH_START, ResizeMode.COVER, false, false);
            case MENU:
                return new Background(PATH_MENU, ResizeMode.COVER, false, false);
            case BATTLE:
                return new Background(PATH_BATTLE, ResizeMode.COVER, false, false);
            case DIALOGUE:
                return new Background(PATH_DIALOGUE, ResizeMode.COVER, false, false);
            case SHOP:
                return new Background(PATH_SHOP, ResizeMode.COVER, false, false);
            case ENDING:
                return new Background(PATH_ENDING, ResizeMode.COVER, false, false);
            case MAIN:
                Background mainBg = new Background(PATH_MAIN, ResizeMode.TILE, true, true);
                mainBg.setParallax(0.5f, 0.5f); // Optional: parallax scrolling
                return mainBg;
            default:
                return new Background(PATH_START, ResizeMode.COVER, false, false);
        }
    }
}
