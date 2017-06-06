package com.wms.dto;

import java.util.List;

/**
 * Created by duyot on 5/17/2017.
 */
public class ChartDTO {
    private String name;
    private int [] data;
    private String [] xAxisData;

    public ChartDTO() {
    }

    public ChartDTO(String name, int[] data, String[] xAxisData) {
        this.name = name;
        this.data = data;
        this.xAxisData = xAxisData;
    }

    public ChartDTO(String name, int [] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }
}
