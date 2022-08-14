package ggc.app.products;

import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.UndefinedProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("productID", ggc.app.products.Prompt.productKey());
  }

  @Override
  public final void execute() throws CommandException {
    try{
      String productID = stringField("productID");
      _display.popup(_receiver.showBatchesByProduct(productID));
    }
    catch(UndefinedProductKeyException e){
      throw new UnknownProductKeyException(e.getId());
    }
  }

}
