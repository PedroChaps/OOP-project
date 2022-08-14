package ggc.app.transactions;

import ggc.app.exceptions.UnavailableProductException;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.LowOnStockException;
import ggc.exceptions.UndefinedPartnerKeyException;
import ggc.exceptions.UndefinedProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerID", Prompt.partnerKey());
    addStringField("productID", Prompt.productKey());
    addIntegerField("amount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      String partnerID = stringField("partnerID");
      String productID = stringField("productID");
      int amount = integerField("amount");

      _receiver.registerBreakdownTransaction(partnerID, productID, amount);
    }
    catch (UndefinedProductKeyException e) {
      throw new UnknownProductKeyException(e.getId());
    }
    catch (UndefinedPartnerKeyException e) {
      throw new UnknownPartnerKeyException(e.getId());
    }
    catch (LowOnStockException e) {
      throw new UnavailableProductException(e.getId(), e.getRequested(), e.getAvailable());
    }
  }

}
