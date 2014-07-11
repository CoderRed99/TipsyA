package com.app.dianna.tipsy;

import java.io.Serializable;

/**
 * Created by dianna on 7/3/14.
 */
public class Guest implements Serializable
{
    private String name;
    private double total, tip, meal;
/*    private double badTip, mediocreTip, avgTip, goodTip, greatTip;*/

    public Guest(String name, double total, double tip, double meal)
/*                , double badTip, double mediocreTip, double avgTip,
                double goodTip, double greatTip)*/
    {
        this.name = name;
        this.total = total;
        this.tip = tip;
        this.meal = meal;

/*        this.badTip = badTip;
        this.mediocreTip = mediocreTip;
        this.avgTip = avgTip;
        this.goodTip = goodTip;
        this.greatTip = greatTip;*/
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getTip()
    {
        return tip;
    }

    public void setTip(double tip)
    {
        this.tip = tip;
    }

    public double getMeal()
    {
        return meal;
    }

    public void setMeal(double meal)
    {
        this.meal = meal;
    }
/*
    public double getBadTip()
    {
        return badTip;
    }

    public void setBadTip(double badTip)
    {
        this.badTip = badTip;
    }

    public double getMediocreTip()
    {
        return mediocreTip;
    }

    public void setMediocreTip(double mediocreTip)
    {
        this.mediocreTip = mediocreTip;
    }

    public double getAvgTip()
    {
        return avgTip;
    }

    public void setAvgTip(double avgTip)
    {
        this.avgTip = avgTip;
    }

    public double getGoodTip()
    {
        return goodTip;
    }

    public void setGoodTip(double goodTip)
    {
        this.goodTip = goodTip;
    }

    public double getGreatTip()
    {
        return greatTip;
    }

    public void setGreatTip(double greatTip)
    {
        this.greatTip = greatTip;
    }*/
}
