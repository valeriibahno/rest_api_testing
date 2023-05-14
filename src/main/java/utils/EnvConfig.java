package utils;

public final class EnvConfig extends PropertyManager {
    private static volatile EnvConfig instance;

    private EnvConfig() {}

    public static EnvConfig getEnvInstance() {
        if(instance == null) {
            instance = new EnvConfig();
        }
        return instance;
    }
}
