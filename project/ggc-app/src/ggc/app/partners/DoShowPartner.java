package ggc.app.partners;

import ggc.app.partners.Prompt;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.Partner;

import ggc.exceptions.UndefinedPartnerKeyException;
import ggc.app.exceptions.UnknownPartnerKeyException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerID", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try{
      String partnerID = stringField("partnerID");
      _display.popup(_receiver.showPartner(partnerID));
    }
    catch(UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
