package ggc.app.products;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.products.Prompt;
import ggc.exceptions.UndefinedPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("partnerID", ggc.app.products.Prompt.partnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    try{
      String partnerID = stringField("partnerID");
      _display.popup(_receiver.showBatchesByPartner(partnerID));
    }
    catch(UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
