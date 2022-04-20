package to.tuc.tp.Model;

/**
 * Modeleaza atributele tabelului "Product" din baza de date
 */
public class Product {
    private Integer productId;
    private String denumire;
    private Float pret;
    private Integer cantitate;

    /**
     * Creaza un obiect instanta a clasei Product
     * @param productId ID-ul produsuliui
     * @param denumire denumirea produsului
     * @param pret pretul produsului
     * @param cantitate cantitatea disponibila in stock
     */
    public Product(Integer productId, String denumire, Float pret, Integer cantitate) {
        this.productId = productId;
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    /**
     * Returneaza ID-ul produsului
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Returneaza denumirea produsului
     */
    public String getDenumire() {
        return denumire;
    }

    /**
     * Returneaza pretului produsului
     */
    public Float getPret() {
        return pret;
    }

    /**
     * Returneaza cantitatea de produs disponibila in stock
     */
    public Integer getCantitate() {
        return cantitate;
    }
}
