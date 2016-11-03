package com.epam.as.xmlparser.entity;

import java.util.Map;

/**
 * Create aggregate Mobile company.
 */
public class MobileCompany {
    private String name;
    private Map<Tariff, Integer> tariffMap;
    private int RANDOM_RANGE = 1500000;


    public MobileCompany() {
    }

    /**
     * Constructs new Mobile Company
     *
     * @param name      the name of Mobile Company
     * @param tariffMap the list of tariffs for Mobile Company
     */
    public MobileCompany(String name, Map<Tariff, Integer> tariffMap) {
        this.name = name;
        this.tariffMap = tariffMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Tariff, Integer> getTariffMap() {
        return tariffMap;
    }

    public void setTariffMap(Map<Tariff, Integer> tariffMap) {
        this.tariffMap = tariffMap;
    }

    /**
     * Generate random number of clients for tariff.
     *
     * @return random number of clients for tariff
     */
    public int getTariffNumberOfClients() {
        return (int) (Math.random() * RANDOM_RANGE);
    }

    /**
     * Get number of customers for all tariff.
     *
     * @param tariffMap the list of tariff
     */
    public int getLogAllCustomers(Map<Tariff, Integer> tariffMap) {
        int count = 0;

        for (Map.Entry m : tariffMap.entrySet())
            count += Integer.parseInt(m.getValue().toString());
        return count;
    }
}
