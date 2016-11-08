package com.epam.as.xmlparser.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregator - Mobile company.
 */
public class MobileCompany {
    private String name;
    private List tariffs;


    public MobileCompany() {
    }

    /**
     * Constructs new Mobile Company
     *
     * @param name      the name of Mobile Company
     * @param tariffs the list of tariffs for Mobile Company
     */
    public MobileCompany(String name, List<Object> tariffs) {
        this.name = name;
        this.tariffs = tariffs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getTariffs() {
        return new ArrayList<>(tariffs);
    }

    public void setTariffs(List tariffs) {
        this.tariffs = tariffs;
    }

}
