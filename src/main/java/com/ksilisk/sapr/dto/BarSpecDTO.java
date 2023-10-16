package com.ksilisk.sapr.dto;

public class BarSpecDTO {

    private double elasticMod;
    private double permisVolt;

    public BarSpecDTO() {
        this(0, 0);
    }

    public BarSpecDTO(double elasticMod, double permisVolt) {
        this.elasticMod = elasticMod;
        this.permisVolt = permisVolt;
    }

    public double getElasticMod() {
        return elasticMod;
    }

    public void setElasticMod(double elasticMod) {
        this.elasticMod = elasticMod;
    }

    public double getPermisVolt() {
        return permisVolt;
    }

    public void setPermisVolt(double permisVolt) {
        this.permisVolt = permisVolt;
    }
}
