/*
 * Copyright (c) 2021. Nikhila (Nikki) Suneel. All Rights Reserved.
 */

package com.golfelf.drivingrange;

public class Picker {
    private int id;
    private String name;
    private String type;
    private int throughput;

    public Picker() {}

    public Picker(String name, String type, int throughput) {
        setName(name);
        setType(type);
        setThroughput(throughput);
    }

    public Picker(int id, String name, String type, int throughput) {
        this(name, type, throughput);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null ) {
            throw new IllegalArgumentException("Name must not be null");
        }
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("type must be not be null");
        }

        if (!type.equalsIgnoreCase("manual") && !type.equalsIgnoreCase("automatic")) {
            throw new IllegalArgumentException("type must be Manual or Automatic");
        }

        this.type = type;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        if (throughput > 5000) {
            throw new IllegalArgumentException("Throughput must be less than 5000");
        }
        this.throughput = throughput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Picker)) {
            return false;
        }
        Picker p = (Picker) obj;

        if (this.id == p.getId()) {
            return true;
        }
        return false;
    }
}
