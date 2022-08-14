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

import javax.swing.*;
//FIXME import classes

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    addStringField("partnerID",Prompt.partnerKey());
    addIntegerField("paymentDeadline",Prompt.paymentDeadline());
    addStringField("productID", Prompt.productKey());
    addIntegerField("amount",Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    try{
      String partnerID = stringField("partnerID");
      int paymentDeadline = integerField("paymentDeadline");
      String productID = stringField("productID");
      int amount = integerField("amount");

      _receiver.registerSaleTransaction(partnerID, paymentDeadline,
                                                   productID, amount);
    }

    catch(UndefinedProductKeyException e){
      throw new UnknownProductKeyException(e.getId());
    }
    catch(UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
    catch (LowOnStockException e){
      throw new UnavailableProductException(e.getId(),
                                  e.getRequested(), e.getAvailable());
    }
  }

}
