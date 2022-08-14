package ggc.app.lookups;

import ggc.app.lookups.Prompt;
import ggc.exceptions.UndefinedProductKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("price", ggc.app.lookups.Prompt.priceLimit());
  }

  @Override
  public void execute() throws CommandException {
    double price = realField("price");
    _display.popup(_receiver.lookupProductBatchesUnderGivenPrice(price));
  }

}
