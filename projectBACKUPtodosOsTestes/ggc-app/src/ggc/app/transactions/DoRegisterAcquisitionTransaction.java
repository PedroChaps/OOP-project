package ggc.app.transactions;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.UndefinedPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import pt.tecnico.uilib.forms.Form;
/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerID", Prompt.partnerKey());
    addStringField("productID",Prompt.productKey());
    addRealField("price",Prompt.price());
    addIntegerField("amount",Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      String partnerID = stringField("partnerID");
      String productID = stringField("productID");
      double price = realField("price");
      int amount = integerField("amount");

      if (_receiver.productExists(productID))
        _receiver.registerAcquisitionTransfer(partnerID, productID, price, amount);

      else {
        Form form = new Form();
        if (!form.confirm(Prompt.addRecipe()))
          _receiver.registerSimpleProduct(productID, amount, price);

        else {
          int nComponents = form.requestInteger(Prompt.numberOfComponents());
          double alpha = form.requestReal(Prompt.alpha());
          String recipe = "";
          for (int ix = 0; ix < nComponents; ix++) {
            String productID1 = form.requestString(Prompt.productKey());
            int amount1 = form.requestInteger(Prompt.amount());
            recipe += productID1 + ":" + amount1 + "#";
          }
          recipe = recipe.substring(0, recipe.length() - 1);
          _receiver.registerDerivedProduct(productID, amount, price, alpha, recipe);
        }
        _receiver.registerAcquisitionTransferNewProduct(partnerID,productID,price,amount);
      }
    }
    catch (UndefinedPartnerKeyException e) {
      throw new UnknownPartnerKeyException(e.getId());
    }
  }

}
