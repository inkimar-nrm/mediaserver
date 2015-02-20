/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.bio.mediaserver.domain.dummy;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ingimar
 */
@Entity
@Table(name = "VEHICLE")
@NamedQueries({
    @NamedQuery(name = "Vehicle.findAll", query = "SELECT v FROM Vehicle v"),
    @NamedQuery(name = "Vehicle.findByUuid", query = "SELECT v FROM Vehicle v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "Vehicle.findByWheels", query = "SELECT v FROM Vehicle v WHERE v.wheels = :wheels"),
    @NamedQuery(name = "Vehicle.findBySpeed", query = "SELECT v FROM Vehicle v WHERE v.speed = :speed")})
@XmlAccessorType(value = XmlAccessType.FIELD)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@XmlRootElement
public abstract class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "UUID")
    private String uuid;

    @Column(name = "WHEELS")
    private Integer wheels;

    @Column(name = "SPEED")
    private Integer speed;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehicle")
//    private Car car;

    public Vehicle() {
    }

    public Vehicle(String uuid) {
        this.uuid = uuid;
    }

    public Vehicle( String uuid,Integer wheels, Integer speed) {
        this.uuid = uuid;
        this.wheels = wheels;
        this.speed = speed;
    }
    
    

//    public Vehicle(Integer wheels, Integer speed, Car car) {
//        this.wheels = wheels;
//        this.speed = speed;
//        this.car = car;
//    }
    

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getWheels() {
        return wheels;
    }

    public void setWheels(Integer wheels) {
        this.wheels = wheels;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
//
//    public Car getCar() {
//        return car;
//    }
//
//    public void setCar(Car car) {
//        this.car = car;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicle)) {
            return false;
        }
        Vehicle other = (Vehicle) object;
        if ((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.bio.mediaserver.domain.Vehicle[ uuid=" + uuid + " ]";
    }
    
}
