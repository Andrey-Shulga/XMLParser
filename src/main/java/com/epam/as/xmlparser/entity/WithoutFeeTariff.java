package com.epam.as.xmlparser.entity;

/**
 * General entities without subscription fee, for all country.
 */
public class WithoutFeeTariff extends Tariff {

    private int callInNetCost;
    private int callOutNetCost;
    private int trafficMbCost;

    public WithoutFeeTariff() {
    }

    /**
     * Construct new tariff without fee.
     *
     * @param id             the ID for tariff
     * @param name           the name of new tariff
     * @param CallInNetCost  cost of call in network per minute
     * @param CallOutNetCost cost of call out network per minute
     * @param TrafficMbCost  cost of traffic per mbytes
     */
    public WithoutFeeTariff(int id, String name, int CallInNetCost, int CallOutNetCost, int TrafficMbCost) {
        super(id, name);
        this.callInNetCost = CallInNetCost;
        this.callOutNetCost = CallOutNetCost;
        this.trafficMbCost = TrafficMbCost;
    }

    public int getCallInNetCost() {
        return callInNetCost;
    }

    public void setCallInNetCost(int callInNetCost) {
        this.callInNetCost = callInNetCost;
    }

    public int getCallOutNetCost() {
        return callOutNetCost;
    }

    public void setCallOutNetCost(int callOutNetCost) {
        this.callOutNetCost = callOutNetCost;
    }

    public int getTrafficMbCost() {
        return trafficMbCost;
    }

    public void setTrafficMbCost(int trafficMbCost) {
        this.trafficMbCost = trafficMbCost;
    }

    /**
     * Return description about tariff without fee, with parameters.
     *
     * @return description about tariff without fee, with parameters.
     */
    public String getDescription() {
        return "Tariff " + getId() + " \"" + getName() + "\".  This tariff without subscription fee, cost of in-net call " + getCallInNetCost() + " (per min.), cost of out-net call "
                + getCallOutNetCost() + " (per min.), cost of traffic " + getTrafficMbCost() + " (per Mb).";
    }

    @Override
    public String getTariffType() {
        return "nofee";
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
