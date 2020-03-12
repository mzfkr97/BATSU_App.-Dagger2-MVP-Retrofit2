package com.roman.batsu.utils.database;

public class DataPojo {

    private double _id ;
    private String
            tile,
            description;
    private double timestamp;

    public DataPojo(double _id, String tile, String description, double timestamp) {
        this._id = _id;
        this.tile = tile;
        this.description = description;
        this.timestamp = timestamp;
    }

    public DataPojo() {
    }

    public double get_id() {
        return _id;
    }

    public void set_id(double _id) {
        this._id = _id;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
