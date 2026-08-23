package utils;

public enum Browser {
    CHROME,
    FIREFOX,
    EDGE;

    public static Browser from(String browserName) {
        if (browserName == null) return CHROME;
        browserName = browserName.trim().toLowerCase();
        switch (browserName) {
            case "firefox":
            case "ff":
                return FIREFOX;
            case "edge":
            case "msedge":
            case "microsoftedge":
                return EDGE;
            case "chrome":
            case "ch":
            default:
                return CHROME;
        }
    }
}