package com.epam.as.xmlparser.entity;

/**
 * General abstract class for all tariffs.
 */
public abstract class Tariff {

    private String name;
    private int id;


    public Tariff() {
    }

    /**
     * Construct new tariff with fee.
     *
     * @param id   the ID for tariff
     * @param name the name of new tariff
     */
    public Tariff(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return description about tariffs.
     */
    public abstract String getDescription();

    public abstract String getTariffType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;

        Tariff tariff = (Tariff) o;

        if (id != tariff.id) return false;
        return name.equals(tariff.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }
}
