package ggc.app.transactions;

import ggc.app.exceptions.UnknownTransactionKeyException;
import ggc.exceptions.UndefinedTransactionKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("transactionID", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    try{
      int transactionID = integerField("transactionID");
      _receiver.receivePayment(transactionID);
    }
    catch(UndefinedTransactionKeyException e){
      throw new UnknownTransactionKeyException(e.getId());
    }
  }

}
