package com.myapp.demotubes.Entities.Properties;

public enum Agama {
    ISLAM("Islam"),
    KRISTEN("Kristen"),
    KATOLIK("Katolik"),
    HINDU("Hindu"),
    BUDDHA("Buddha"),
    KONGHUCU("Konghucu");

    private String displayName;

    Agama(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
