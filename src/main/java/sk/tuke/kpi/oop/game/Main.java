package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Game;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.WindowSetup;
import sk.tuke.kpi.gamelib.World;
import sk.tuke.kpi.oop.game.addon.scenarios.Stakeout;

public class Main {
    public static void main(String[] args){
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup);

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
        Scene scene = new World("world", "maps/stakeout.tmx", new Stakeout.Factory());

        // pridanie sceny do hry
        game.addScene(scene);

        scene.addListener(new Stakeout());

        // spustenie hry
        game.start();

        scene.getInput().onKeyPressed(key -> {
            if (key == Input.Key.ESCAPE) {
                scene.getGame().stop();
            }
        });
    }
}
