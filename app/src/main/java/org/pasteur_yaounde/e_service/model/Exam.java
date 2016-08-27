package org.pasteur_yaounde.e_service.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maelfosso on 8/26/16.
 */
public class Exam  implements Serializable {

    private String name;
    private String description;
    private Long price;
    private boolean isInReduction;
    private int duration;
    private List<String> others;

    private boolean appointment = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isInReduction() {
        return isInReduction;
    }

    public void setInReduction(boolean inReduction) {
        isInReduction = inReduction;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

    public boolean isAppointment() {
        return appointment;
    }

    public void setAppointment(boolean appointment) {
        this.appointment = appointment;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isInReduction=" + isInReduction +
                ", duration=" + duration +
                ", others=" + others +
                '}';
    }
}
