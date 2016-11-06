package com.epam.as.xmlparser.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class for entities with subscription fee.
 */
@XmlRootElement
public class Tariff implements Comparable<Tariff> {
    private String name;
    private int id;
    private int fee;
    private int includedMinutes;
    private int includedTraffic;

    public Tariff() {

    }

    /**
     * Construct new tariff with fee.
     *
     * @param id              the ID for tariff
     * @param name            the name of new tariff
     * @param fee             cost of fee per month.
     * @param includedMinutes number of free time (minutes) included in entities
     * @param includedTraffic number of free traffic (mbytes) included in entities
     */
    public Tariff(int id, String name, int fee, int includedMinutes, int includedTraffic) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.includedMinutes = includedMinutes;
        this.includedTraffic = includedTraffic;

    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getIncludedMinutes() {
        return includedMinutes;
    }

    public void setIncludedMinutes(int includedMinutes) {
        this.includedMinutes = includedMinutes;
    }

    public int getIncludedTraffic() {
        return includedTraffic;
    }

    public void setIncludedTraffic(int includedTraffic) {
        this.includedTraffic = includedTraffic;
    }

    /**
     * Return description about tariff with parameters.
     *
     * @return description about tariff with parameters
     */
    private String getDescription() {
        return "Tariff " + getId() + " \"" + getName() + "\".  This tariff with subscription fee " + getFee() + " per month. Included free " + getIncludedMinutes() + " min. and " +
                getIncludedTraffic() + " mbs.";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;

        Tariff tariff = (Tariff) o;

        return id == tariff.id && name.equals(tariff.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return getDescription();
    }


    public int compareTo(Tariff other) {
        return Integer.compare(fee, other.fee);
    }
}