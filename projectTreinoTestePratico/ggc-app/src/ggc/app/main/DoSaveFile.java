package ggc.app.main;

import ggc.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.save();
    }
    catch (MissingFileAssociationException e) {

      boolean success = false;
      Form form = new Form();

      while (!success) {
        String filename = form.requestString(Prompt.newSaveAs());
        try {
          _receiver.saveAs(filename);
          success = true;
        }
        catch (FileNotFoundException e2){}
        catch (IOException e2){e2.printStackTrace();}
      }

    }
    catch (IOException e){e.printStackTrace();}

  }
}
