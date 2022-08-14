package ggc.app.partners;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.UndefinedPartnerKeyException;
import ggc.exceptions.UndefinedProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("partnerID", Prompt.partnerKey());
    addStringField("productID", Prompt.productKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerID = stringField("partnerID");
      String productID = stringField("productID");
      _receiver.toggleProductNotifications(partnerID, productID);
    }
    catch (UndefinedProductKeyException e){
      throw new UnknownProductKeyException(e.getId());
    }
    catch (UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }

  }

}
