package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;
import sk.tuke.kpi.oop.game.scenarios.TrainingGameplay;

public class Main {
    public static void main(String[] args){
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup);

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
        Scene scene = new World("world", "maps/mission-impossible.tmx", new MissionImpossible.Factory());

        // pridanie sceny do hry
        game.addScene(scene);

        scene.addListener(new MissionImpossible());

        // spustenie hry
        game.start();

        scene.getInput().onKeyPressed(key -> {
            if (key == Input.Key.ESCAPE) {
                scene.getGame().stop();
            }
        });
    }
}
