package to.tuc.tp.Presentation;

import to.tuc.tp.DataAccess.ClientDAO;

public interface IClientView {
    public String getNumeV();
    public String getPrenumeV();
    public Integer getIdV();
    public String getAdresaV();
    public void TabelClienti(ClientDAO clientList);
    public String[] getInformatiiRand();
}
