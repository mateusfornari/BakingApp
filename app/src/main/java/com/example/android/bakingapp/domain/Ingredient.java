package com.example.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mateus on 15/06/17.
 */

public class Ingredient implements Parcelable{
    private String description;
    private double quantity;
    private String measure;

    public Ingredient(){}

    protected Ingredient(Parcel in) {
        description = in.readString();
        quantity = in.readDouble();
        measure = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeDouble(quantity);
        dest.writeString(measure);
    }
}
