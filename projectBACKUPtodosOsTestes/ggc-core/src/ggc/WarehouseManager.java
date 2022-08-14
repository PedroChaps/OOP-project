package ggc;

import java.io.*;

import ggc.exceptions.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.*;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current store. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  private boolean hasFilename() {
    return _filename != "";
  }


  public void advanceDate(int slack) throws BadDateException {
    _warehouse.processAdvanceDate(slack);
  }

  public int displayDate() {
    return _warehouse.processDisplayDate();
  }


  public void registerPartner(String partnerID, String name,
                              String address) throws
          SamePartnerKeyException {
    _warehouse.processRegisterPartner(partnerID, name, address);
  }

  public String showPartner(String partnerID) throws
          UndefinedPartnerKeyException {
    return _warehouse.processShowPartner(partnerID).toString();
  }

  public List<?> showBatchesByPartner(String partnerID) throws UndefinedPartnerKeyException {
    return _warehouse.processShowBatchesByPartner(partnerID);
  }

  public List<?> showBatchesByProduct(String productID) throws UndefinedProductKeyException {
    return _warehouse.processShowBatchesByProduct(productID);
  }

  public List<?> showAllPartners(){
    return _warehouse.processShowAllPartners();
  }

  public List<?> showAllProducts(){
    return _warehouse.processShowAllProducts();
  }

  public List<?> showAvailableBatches() {
    return _warehouse.processShowAvailableBatches();
  }

  public double ShowAvaliableBalance(){
    return _warehouse.getAvaliableBalance();
  }

  public double ShowAccountingBalance(){
    return _warehouse.getAccountingBalance();
  }

  public List<?> lookupProductBatchesUnderGivenPrice(double price){
    return _warehouse.processLookupProductBatchesUnderGivenPrice(price);
  }

  public void receivePayment(int transactionID) throws UndefinedTransactionKeyException {
    _warehouse.processReceivePayment(transactionID);
  }

  public String showTransaction(int transactionID) throws UndefinedTransactionKeyException {
    return _warehouse.processShowTransaction(transactionID);
  }

  public List<?> lookupPaymentsByPartner(String partnerID) throws UndefinedPartnerKeyException {
    return _warehouse.processLookupPaymentsByPartner(partnerID);
  }

  public void registerBreakdownTransaction(String partnerID, String productID, int amount) throws UndefinedProductKeyException, UndefinedPartnerKeyException, LowOnStockException {
    _warehouse.processRegisterBreakdownTransaction(partnerID, productID, amount);
  }

  public void registerAcquisitionTransfer(String partnerID, String productID, double price, int amount) throws UndefinedPartnerKeyException{
    _warehouse.processRegisterAcquisition(partnerID,productID,price,amount);
  }
  public void registerAcquisitionTransferNewProduct(String partnerID, String productID, double price, int amount) throws UndefinedPartnerKeyException{
    _warehouse.processRegisterAcquisitionNewProduct(partnerID,productID,price,amount);
  }

  public boolean productExists(String productID){return _warehouse.productExists(productID);}

  public void registerSimpleProduct(String productID, int stock, double price){
    _warehouse.registerSProduct(productID,stock,price);
  }
  public void registerDerivedProduct(String productID, int stock, double price, double alpha, String recipe){
    _warehouse.registerDerivedProduct(productID,stock,price,alpha,recipe);
  }
  public void registerSaleTransaction(String partnerID, int paymentDeadline, String productID, int amount) throws LowOnStockException,UndefinedPartnerKeyException,UndefinedProductKeyException{
    _warehouse.processRegisterSaleTransaction(partnerID,paymentDeadline,productID,amount);
  }

  public void toggleProductNotifications(String partnerID, String productID) throws UndefinedProductKeyException, UndefinedPartnerKeyException {
    _warehouse.processToggleProductNotifications(partnerID, productID);
  }

  public List<?> showPartnerSales(String partnerID) throws UndefinedPartnerKeyException {
    return _warehouse.processShowBreakdownsAndSalesByPartner(partnerID);
  }

  public List<?> showPartnerAcquisitions(String partnerID) throws UndefinedPartnerKeyException {
    return _warehouse.processShowAcquisitionsByPartner(partnerID);
  }

  // -----------------------------------------------------------------------------------


  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws MissingFileAssociationException,
          IOException {

    if (!hasFilename()){throw new MissingFileAssociationException();}

    ObjectOutputStream writer = new ObjectOutputStream
            (new BufferedOutputStream
                    (new FileOutputStream(_filename)));
    writer.writeObject(_warehouse);
    writer.close();
  }



  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws
          FileNotFoundException, IOException {
    try {
      _filename = filename;
      save();
    }
    catch (MissingFileAssociationException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try {
      ObjectInputStream reader = new ObjectInputStream
              (new BufferedInputStream(new FileInputStream(filename)));
      _warehouse = (Warehouse) reader.readObject();
      reader.close();
      _filename = filename;
    }
    catch (IOException | ClassNotFoundException e){
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
	    _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException e) {
	    throw new ImportFileException(textfile);
    }
  }

  public void importFile1(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile1(textfile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile);
    }
  }
}
