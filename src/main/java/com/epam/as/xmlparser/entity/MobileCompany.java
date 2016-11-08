package com.epam.as.xmlparser.entity;

import java.util.Map;

/**
 * Aggregator - Mobile company.
 */
public class MobileCompany {
    private String name;
    private Map<Tariff, Integer> tariffs;
    private int RANDOM_RANGE = 1500000;


    public MobileCompany() {
    }

    /**
     * Constructs new Mobile Company
     *
     * @param name      the name of Mobile Company
     * @param tariffs the list of tariffs for Mobile Company
     */
    public MobileCompany(String name, Map<Tariff, Integer> tariffs) {
        this.name = name;
        this.tariffs = tariffs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Tariff, Integer> getTariffs() {
        return tariffs;
    }

    public void setTariffs(Map<Tariff, Integer> tariffs) {
        this.tariffs = tariffs;
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
