package to.tuc.tp.Presentation;

import to.tuc.tp.DataAccess.OrderDAO;

public interface IOrderView {
    public Integer getIdClient();
    public Integer getIdProdus();
    public String[] getInformatiiRand();
    public void warningCantitate();
    public void TabelComenzi(OrderDAO orderList);
}
