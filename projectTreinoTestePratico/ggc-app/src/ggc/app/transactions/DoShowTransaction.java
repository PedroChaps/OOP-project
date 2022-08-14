package ggc.app.transactions;

import ggc.app.exceptions.UnknownTransactionKeyException;
import ggc.exceptions.UndefinedTransactionKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("transactionID", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      int transactionID = integerField("transactionID");
      _display.popup(_receiver.showTransaction(transactionID));
    }
    catch(UndefinedTransactionKeyException e){
      throw new UnknownTransactionKeyException(e.getId());
    }
  }

}
