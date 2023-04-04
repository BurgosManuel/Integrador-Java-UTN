package enums;

public enum ResultadoEnum {
    GANADOR("GANADOR"),
    PERDEDOR("PERDEDOR"),
    EMPATE("EMPATE");

    private String value;

    ResultadoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
