package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.Warehouse;
// Importar as exceções
import ggc.exceptions.SamePartnerKeyException;
import ggc.app.exceptions.DuplicatePartnerKeyException;
import ggc.app.exceptions.UnknownPartnerKeyException;


import ggc.WarehouseManager;
//FIXME import classes

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("partnerName", Prompt.partnerName());
    addStringField("partnerID", Prompt.partnerID());
  }

  @Override
  public void execute() throws CommandException {
  //P tenta criar um parceiro. Se correr mal, lança o erro para a app
    try{
      String partnerName = stringField("partnerName");
      String partnerID = stringField("partnerID");

      _receiver.changePartnerName(partnerID, partnerName);
    }
    catch(UndefinedPartnerKeyException e){
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
