package model;

public class Currency {
    private Integer id_currency;
    private String code;
    private String name;

    public Currency(Integer id_currency, String code, String name) {
        this.id_currency = id_currency;
        this.code = code;
        this.name = name;
    }

    public Integer getId_currency() {
        return id_currency;
    }

    public void setId_currency(Integer id_currency) {
        this.id_currency = id_currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id_currency=" + id_currency +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
