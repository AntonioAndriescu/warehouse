package to.tuc.tp.Model;

/**
 * Modeleaza atributele tabelului "Client" din baza de date
 */
public class Client {
    private Integer clientId;
    private String nume;
    private String prenume;
    private String adresa;

    /**
     * Creaza un obiect instanta a clasei Client
     * @param clientId valoarea ID-ului clientului
     * @param nume numele clientului
     * @param prenume prenumele clientului
     * @param adresa adresa clientului
     */
    public Client(Integer clientId, String nume, String prenume, String adresa) {
        this.clientId = clientId;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
    }

    /**
     * Returneaza ID-ul clientului
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Returneaza numele clientului
     */
    public String getNume() {
        return nume;
    }

    /**
     * Returneaza prenumele clientului
     */
    public String getPrenume() {
        return prenume;
    }

    /**
     * Returneaza adresa clientului
     */
    public String getAdresa() {
        return adresa;
    }
}
