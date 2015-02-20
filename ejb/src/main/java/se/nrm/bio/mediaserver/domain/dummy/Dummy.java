package se.nrm.bio.mediaserver.domain.dummy;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "DUMMY")
@NamedQueries({
    @NamedQuery(name = Dummy.FIND_All, query = "SELECT d FROM Dummy d"),
    @NamedQuery(name = "Dummy.findById", query = "SELECT d FROM Dummy d WHERE d.id = :id"),
    @NamedQuery(name = "Dummy.FindByTitle", query = "SELECT d FROM Dummy d WHERE d.title = :title"),
    @NamedQuery(name = "Dummy.findByPrice", query = "SELECT d FROM Dummy d WHERE d.price = :price"),
    @NamedQuery(name = "Dummy.findByDescription", query = "SELECT d FROM Dummy d WHERE d.description = :description"),
    @NamedQuery(name = "Dummy.findByIsbn", query = "SELECT d FROM Dummy d WHERE d.isbn = :isbn"),
    @NamedQuery(name = "Dummy.findByNrOfPage", query = "SELECT d FROM Dummy d WHERE d.nrOfPage = :nrOfPage"),
    @NamedQuery(name = "Dummy.findByIllustrations", query = "SELECT d FROM Dummy d WHERE d.illustrations = :illustrations")})
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Dummy implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_BY_TITLE = "FindByTitle";

    public static final String FIND_All = "FindAll";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "NR_OF_PAGE")
    private String nrOfPage;

    @Column(name = "ILLUSTRATIONS")
    private String illustrations;

    public Dummy() {
    }

    public Dummy(String title, Integer price, String description, String isbn, String nrOfPage, String illustrations) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.isbn = isbn;
        this.nrOfPage = nrOfPage;
        this.illustrations = illustrations;
    }

    public Dummy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNrOfPage() {
        return nrOfPage;
    }

    public void setNrOfPage(String nrOfPage) {
        this.nrOfPage = nrOfPage;
    }

    public String getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(String illustrations) {
        this.illustrations = illustrations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dummy)) {
            return false;
        }
        Dummy other = (Dummy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.nrm.bio.mediaserver.domain.Dummy[ id=" + id + " ]";
    }

}
