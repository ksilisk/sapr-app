package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarSpecDTO {
    @JsonProperty("elastic_mod")
    private double elasticMod;
    @JsonProperty("permis_volt")
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BarSpecDTO that = (BarSpecDTO) obj;
        if (that.getPermisVolt() != permisVolt) return false;
        return that.getElasticMod() == elasticMod;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(elasticMod);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(permisVolt);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BarSpecDTO{" +
                "elasticMod=" + elasticMod +
                ", permisVolt=" + permisVolt +
                '}';
    }
}
