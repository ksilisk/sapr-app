package com.ksilisk.sapr.service;

import com.ksilisk.sapr.payload.Construction;

public enum ConstructionStorage {
    INSTANCE;

    private Construction construction;

    public synchronized Construction getConstruction() {
        return construction;
    }

    public synchronized void setConstruction(Construction construction) {
        this.construction = construction;
    }
}
