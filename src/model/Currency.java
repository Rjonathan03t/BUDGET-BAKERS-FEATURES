package model;

public class Currency {
    private Integer id_currency;
    private String name;
    private String code;
    

    public Currency(Integer id_currency, String name, String code) {
        this.id_currency = id_currency;
        this.name = name;
        this.code = code;
        
    }

    public Integer getId_currency() {
        return id_currency;
    }

    public void setId_currency(Integer id_currency) {
        this.id_currency = id_currency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    

    @Override
    public String toString() {
        return "Currency{" +
                "id_currency=" + id_currency +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                 '}';
    }
}
