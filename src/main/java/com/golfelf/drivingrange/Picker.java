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
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getThroughput() {
        return throughput;
    }

    public void setThroughput(int throughput) {
        this.throughput = throughput;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
