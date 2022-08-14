package ggc.app;

import ggc.app.exceptions.UnknownProductKeyException;
import ggc.exceptions.UndefinedProductKeyException;
import pt.tecnico.uilib.Dialog;
import pt.tecnico.uilib.menus.Menu;
import ggc.WarehouseManager;
import ggc.exceptions.ImportFileException;

/** Main driver for the management application. */
public class App {

  /** @param args command line arguments. */
  public static void main(String[] args) {
    try (var ui = Dialog.UI) {
      WarehouseManager manager = new WarehouseManager();

      String datafile = System.getProperty("import");
      if (datafile != null) {
        try {
          manager.importFile(datafile);
        } catch (ImportFileException e) {
          // no behavior described: just present the problem
          e.printStackTrace();
        }
      }

      // TODO REMOVE THIS FFS
      if (false) {
        String string =
                "PARTNER|S1|Toshiba|Tokyo, Japan\n" +
                        "PARTNER|M1|Rohit Figueiredo|New Delhi, India\n" +
                        "BATCH_S|ROLHA|M1|20|500\n" +
                        "BATCH_S|VIDRO|M1|30|500\n" +
                        "BATCH_M|GARRAFA|S1|50|1000|0.1|VIDRO:1#ROLHA:1\n" +
                        "BATCH_M|GARRAFA|S1|80|1000|0.1|VIDRO:1#ROLHA:1\n";
        try {
          manager.importFile1(string);
        } catch (ImportFileException e) {
          e.printStackTrace();
        }
      }

      Menu menu = new ggc.app.main.Menu(manager);
      menu.open();
    }
  }

}
