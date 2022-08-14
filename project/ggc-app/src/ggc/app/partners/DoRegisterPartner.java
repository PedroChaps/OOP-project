package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import ggc.Warehouse;
// Importar as exceções
import ggc.exceptions.SamePartnerKeyException;
import ggc.app.exceptions.DuplicatePartnerKeyException;


import ggc.WarehouseManager;
//FIXME import classes

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("id", Prompt.partnerKey());
    addStringField("name", Prompt.partnerName());
    addStringField("address", Prompt.partnerAddress());
  }

  @Override
  public void execute() throws CommandException {
  //P tenta criar um parceiro. Se correr mal, lança o erro para a app
    try{
      String id = stringField("id");
      String name = stringField("name");
      String address = stringField("address");

      _receiver.registerPartner(id, name, address);
    }
    catch(SamePartnerKeyException e){
      throw new DuplicatePartnerKeyException(e.getId());
    }
  }

}
