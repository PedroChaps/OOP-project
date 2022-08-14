package ggc.app.lookups;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.lookups.Prompt;
import ggc.exceptions.UndefinedPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partnerID", ggc.app.lookups.Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try{
      String partnerID = stringField("partnerID");
      _display.popup(_receiver.lookupPaymentsByPartner(partnerID));
    }
    catch(UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
