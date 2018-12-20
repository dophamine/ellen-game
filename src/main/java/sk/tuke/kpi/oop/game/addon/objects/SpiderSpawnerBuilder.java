package sk.tuke.kpi.oop.game.addon.objects;

// Pattern Builder
public class SpiderSpawnerBuilder extends SpiderSpawner {

    public SpiderSpawnerBuilder(String type) {
        super(type);
    }

    public SpiderSpawner build() {
        return this;
    }

    public SpiderSpawnerBuilder waves(int waves) {
        setWaves(waves);

        return this;
    }

    public SpiderSpawnerBuilder waveSize(int waveSize) {
        setWaveSize(waveSize);

        return this;
    }

    public SpiderSpawnerBuilder delay(int delay) {
        setDelay(delay);

        return this;
    }
}
