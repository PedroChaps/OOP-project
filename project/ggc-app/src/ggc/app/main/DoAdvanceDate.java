package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

// Importar as exceções
import ggc.exceptions.BadDateException;
import ggc.app.exceptions.InvalidDateException;

import ggc.WarehouseManager;


/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  public DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("days", Prompt.daysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      int days = integerField("days");

      _receiver.advanceDate(days);
    }
    catch(BadDateException e){
      throw new InvalidDateException(e.getDate());
    }
  }

}
