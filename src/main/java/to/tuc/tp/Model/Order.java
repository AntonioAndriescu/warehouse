package to.tuc.tp.Model;

/**
 * Modeleaza atributele tabelului "Orders" din baza de date
 */
public class Order {
    private Integer orderId;
    private Integer clientId;
    private Integer productId;
    private Integer quantity;
    private Float pretTotal;

    /**
     * Creaza un obiect instanta a clasei Order
     * @param orderId ID-ul comenzii
     * @param clientId ID-ul clientului care face comanda
     * @param productId ID-ul produsului comandat
     * @param quantity Cantitatea de produs comandata
     * @param pretTotal Pretul total al comenzii
     */
    public Order(Integer orderId, Integer clientId, Integer productId, Integer quantity, Float pretTotal) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.pretTotal = pretTotal;
    }

    /**
     * Returneaza ID-ul comenzii
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * Returneaza ID-ul clientlui care a facut comanda
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Returneaza ID-ul produsului comandat
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Returneaza cantitatea de produs comandata
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Returneaza pretul total al comenzii
     */
    public Float getPretTotal() {
        return pretTotal;
    }
}
