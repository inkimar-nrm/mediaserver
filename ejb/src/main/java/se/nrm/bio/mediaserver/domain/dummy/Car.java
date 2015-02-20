/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.bio.mediaserver.domain.dummy;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ingimar
 */
@Entity
@Table(name = "CAR")
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findByUuid", query = "SELECT c FROM Car c WHERE c.uuid = :uuid")
})
@XmlRootElement
@DiscriminatorValue("CAR")
public class Car extends Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @Basic(optional = false)
//    @Column(name = "UUID")
//    private String uuid;

//    @Column(name = "DRIVER")
//    private String driver;

//    @JoinColumn(name = "UUID", referencedColumnName = "UUID", insertable = false, updatable = false)
//    @OneToOne(optional = false)
//    private Vehicle vehicle;

    public Car() {
    }

    
     public Car(String uuid, Integer wheels, Integer speed) {
         super(uuid,wheels, speed);
    }
//
//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }

//    public String getDriver() {
//        return driver;
//    }
//
//    public void setDriver(String driver) {
//        this.driver = driver;
//    }
//
//    public Vehicle getVehicle() {
//        return vehicle;
//    }
//
//    public void setVehicle(Vehicle vehicle) {
//        this.vehicle = vehicle;
//    }

    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (uuid != null ? uuid.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Car)) {
//            return false;
//        }
//        Car other = (Car) object;
//        if ((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid))) {
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return "se.nrm.bio.mediaserver.domain.Car[ uuid=" + super.getUuid() + " ]";
    }
    
}
