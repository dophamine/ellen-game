package sk.tuke.kpi.oop.game.characters;

import java.util.HashSet;
import java.util.Set;

public class Health {
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    private int value;
    private int maxValue;
    private Set<ExhaustionEffect> effectListeners = new HashSet<>();

    public Health(int value) {
        this.value = this.maxValue = value;
    }

    public Health(int initialValue, int maxValue) {
        this.value = initialValue;
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    private void setValue(int newValue) {
        this.value = newValue < 0 ? 0 : newValue > maxValue ? maxValue : newValue;

        if (value == 0) {
            for (ExhaustionEffect effect: effectListeners) {
                effect.apply();
            }

            effectListeners.forEach(effect -> effect.apply());
            effectListeners.clear();
        }
    }

    public void refill(int amount) {
        setValue(this.value + amount);
    }

    public void restore() {
        setValue(maxValue);
    }

    public void drain(int amount) {
        setValue(this.value - amount);
    }

    public void exhaust() {
        setValue(0);
    }

    public void onExhaustion(ExhaustionEffect effect) {
        effectListeners.add(effect);
    }
}
