package ggc.app.partners;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.UndefinedPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partnerID", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerID = stringField("partnerID");
      _display.popup(_receiver.showPartnerAcquisitions(partnerID));
    }
    catch (UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
