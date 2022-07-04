package com.example.test_androidramihamdi.model;

import java.util.List;

public class RecyclerList {
    private List<RecyclerData> hits;

    public RecyclerList(List<RecyclerData> hits) {
        this.hits = hits;
    }

    public List<RecyclerData> getHits() {
        return hits;
    }

    public void setHits(List<RecyclerData> hits) {
        this.hits = hits;
    }
}
