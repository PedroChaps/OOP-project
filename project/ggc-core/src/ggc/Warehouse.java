package ggc;

import java.io.*;

import java.util.*;

import ggc.exceptions.*;

/**
 * Class Warehouse implements a warehouse.
 */

public class Warehouse implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  /** Number of existent batches. */
  private int _batchesID = 0;

  private int _transactionsID = 0;

  /** Current date. */
  private int _date = 0;

  /** Global balance. */
  private double _availableBalance = 0;
  private double _accountingBalance = 0;

  /** HashMap to store all the partners. */
  private HashMap<String, Partner> _partners = new HashMap<>();

  /** HashMap to store all the products. */
  private HashMap<String, Product> _products = new HashMap<>();

  /** HashMap to store all the batches. The key is the product ID. */
  private HashMap<Product,TreeSet<Batch>> _batches = new HashMap<>();

  private HashMap<Partner,List<Transaction>> _transactions
          = new HashMap<>();

  /** @return the current number of batches.*/
  public int getBatchesID(){
    return _batchesID;
  }

  /** Increments in one unity the number of existent batches. */
  private void incBatchesID(){
    _batchesID++;
  }
  private DeliveryMethod getDeliveryMethod(){return new Default();}
  private void incTransactionsID(){
    _transactionsID++;
  }
  public int getTransactionsID(){return _transactionsID;}
  public int getDate(){
    return _date;
  }

  public double getAvailableBalance(){
    return _availableBalance;
  }

  public double getAccountingBalance(){
    return _accountingBalance;
  }

  public void updateAvailableBalance(double slack){
    _availableBalance += slack;
  }

  public void updateAccountingBalance(double slack){
    _accountingBalance += slack;
  }

  public Transaction findTransaction(int transactionID){
    for (Partner p: _partners.values()){
      List<Transaction> transactions = _transactions.get(p);
      if (transactions != null)
        for (Transaction t : transactions){
          if (transactionID == t.getId())
            return t;
        }
    }
    return null;
  }

  /** @param slack the number of days to advance.
   *  @throws BadDateException if the number of days is not positive.*/
  public void processAdvanceDate(int slack) throws BadDateException {
    if (slack <= 0) {
      throw new BadDateException(slack);
    }
    _date += slack;
  }

  /** @return the current date.*/
  public int processDisplayDate(){
    return _date;
  }

  /** Creates a new partner.
   * @param partnerID id of the partner.
   *  @param name  name of the partner.
   *  @param address address of the partner.
   *  @throws SamePartnerKeyException if the partner already exists.
   */
  public void processRegisterPartner(String partnerID, String name,
                                     String address) throws
          SamePartnerKeyException {
      if(_partners.containsKey(partnerID.toLowerCase()))
        throw new SamePartnerKeyException(partnerID);
      Partner partner = new Partner(partnerID,name,address);
      _partners.put(partnerID.toLowerCase(),partner);
      for (Map.Entry<String, Product> entry : _products.entrySet())
        entry.getValue().registerObserver(partner);

  }

  /** @return  a list with  all partners*/
  public List<Partner> processShowAllPartners(){
    List<Partner> partnersInfo = new LinkedList<>();

    for (Map.Entry<String, Partner> entry : _partners.entrySet()) {
      partnersInfo.add(entry.getValue());
    }

    Collections.sort(partnersInfo);
    return partnersInfo;
  }



  /** @return  a list of all products.*/
  public List<Product> processShowAllProducts(){
    List<Product> productsInfo = new LinkedList<Product>();
    try {
      for (Map.Entry<String, Product> entry : _products.entrySet()) {
      //for(String s : _products.keySet())
        productsInfo.add(processShowProduct(entry.getValue().getId()));
      }
    }
    catch (UndefinedProductKeyException e){e.printStackTrace();}

    Collections.sort(productsInfo);
    return productsInfo;
  }

  /** @return a list off all batches.*/
  public List<Batch> processShowAvailableBatches(){
    List<Batch> batchesInfo = new LinkedList<Batch>();

    for (Map.Entry<Product, TreeSet<Batch>> entry :
            _batches.entrySet()) {
      for(Batch b : entry.getValue())
      if(!b.soldOutBatch()) {
        batchesInfo.add(b);
      }
    }

    Collections.sort(batchesInfo);
    return batchesInfo;
  }


  /** Searches for the partner with that id and returns it.
   * @param partnerID the id of the partner.
   *  @return  the partner with that id.
   *  @throws UndefinedPartnerKeyException if the id given does not
   *  exist.
   */
  public String processShowPartner(String partnerID) throws
          UndefinedPartnerKeyException {
    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null){
      throw new UndefinedPartnerKeyException(partnerID);
    }

    String out = p.toString();
    if (p.hasNotifications()) {
      List<Notification> notifications = p.getNotifications();
      for (Notification n : notifications)
        out += "\n" + n.toString();
      p.removeAllNotifications();
    }
    return out;
  }

  /**Searches for the product with that id and returns it.
   * @param productID the id of the product.
   * @throws UndefinedProductKeyException if the id given does not
   * exist.
   */
  public Product processShowProduct(String productID) throws
          UndefinedProductKeyException {
    Product p = _products.get(productID);
    if (p == null){throw new UndefinedProductKeyException(productID);}
  return p;
  }


  /**Creates a new batch of a simple product with that id,
   * associated to the partner with that id. The batch has that
   * price associated and that quantity of that product.
   * It also adds the batch to the collection of all batches.
   * @param productID the id of the product.
   * @param partnerID the id of the partner.
   * @param price the price.
   * @param stock the number of elements of the product with that id.
   * @throws UndefinedPartnerKeyException if the id given does not
   * exist.
   */
  public void processRegisterSBatch(String productID, String partnerID,
                                    double price, int stock) throws
           UndefinedPartnerKeyException {

    if (!_partners.containsKey(partnerID.toLowerCase())) {
      throw new UndefinedPartnerKeyException(partnerID);
    }

    if (_products.containsKey(productID)){
      updateProductInfo(productID, price, stock);
    }

    else {
      registerSProduct(productID, stock, price);
    }
    Batch batch = new Batch(_partners.get(partnerID.toLowerCase()),
            _products.get(productID), getBatchesID(), stock, price);
    addBatch(_products.get(productID),batch);
    incBatchesID();
  }



  /**Creates a new batch of a derived product with that id, associated
   * to the partner with that id. The batch has that
   * price associated and that quantity of that product.
   * It also adds the batch to the collection of all batches.
   * @param productID the id of the product.
   * @param partnerID the id of the partner.
   * @param price the price.
   * @param stock the number of elements of the product with that id.
   * @param alpha the aggravating factor associated.
   * @param recipe the recipe of that product.
   * @throws UndefinedPartnerKeyException if the id given does not
   * exist.
   */
  public void processRegisterMBatch(String productID, String partnerID,
                                    double price, int stock,
                                    double alpha, Recipe recipe) throws
          UndefinedPartnerKeyException{

    if (!_partners.containsKey(partnerID.toLowerCase())) {
      throw new UndefinedPartnerKeyException(partnerID);
    }

    if (_products.containsKey(productID)){
      updateProductInfo(productID, price, stock);
    }

    else {
      registerMProduct(productID, stock, price, alpha, recipe);
    }

    Batch batch = new Batch(_partners.get(partnerID.toLowerCase()),
            _products.get(productID), getBatchesID(), stock, price);
    addBatch(_products.get(productID), batch);
    incBatchesID();
  }

  public List<Batch> processShowBatchesByPartner(String partnerID)
          throws UndefinedPartnerKeyException {

    if (!_partners.containsKey(partnerID.toLowerCase())) {
      throw new UndefinedPartnerKeyException(partnerID);
    }

    List<Batch> allBatches = processShowAvailableBatches();
    List<Batch> specificBatches = new ArrayList<>();

    for (Batch b : allBatches){
      if (b.getPartner().getId().equals(partnerID)){
        specificBatches.add(b);
      }
    }

    return specificBatches;
  }

  public List<Batch> processShowBatchesByProduct(String productID)
          throws UndefinedProductKeyException {

    if (!_products.containsKey(productID)) {
      throw new UndefinedProductKeyException(productID);
    }

    List<Batch> allBatches = processShowAvailableBatches();
    List<Batch> specificBatches = new ArrayList<>();

    for (Batch b : allBatches){
      if (b.getProduct().getId().equals(productID)){
        specificBatches.add(b);
      }
    }

    return specificBatches;
  }

  public List<Batch> processLookupProductBatchesUnderGivenPrice
          (double price) {

    List<Batch> allBatches = processShowAvailableBatches();
    List<Batch> specificBatches = new ArrayList<>();

    for (Batch b : allBatches){
      if (b.getPrice() < price){
        specificBatches.add(b);
      }
    }
    return specificBatches;
  }

  public void processRegisterSaleTransaction(String partnerID, int
          paymentDeadline, String productID, int amount) throws
          LowOnStockException,
          UndefinedPartnerKeyException, UndefinedProductKeyException{

    Partner partner = _partners.get(partnerID.toLowerCase());
    Product product = _products.get(productID);
    if (product == null)
      throw new UndefinedProductKeyException(productID);
    if (partner == null)
      throw new UndefinedPartnerKeyException(partnerID);

    try{
      if (!product.checkAvailability(amount))
        throw new LowOnStockException(productID, amount,
                product.getStock());

      double price = 0;
      double maxPricePerUnit = 0;
      int amountSold = amount;

      if(amount<=product.getStock()) {
        price = sellBatches(product, amount);
        maxPricePerUnit = price / amount;
      }
      else {
        int amountLeft = amount;
        for (Batch b : _batches.get(product)) {
          if (!b.soldOutBatch()) {
            amountLeft -= b.getAmount();
            price += b.getAmount() * b.getPrice();
            b.sellout();
          }
        }
        amountSold = amount - amountLeft;
        while (amountLeft != 0) {
          maxPricePerUnit = product.acceptSale
                  (new SaleVisitor(this));
          price += maxPricePerUnit;
          amountLeft--;
        }
        updateProductStock(product, -amountSold);
      }

      Sale t = new Sale(getTransactionsID(), amount, price,
              partner, product, paymentDeadline);
      updateProductMaxPrice(product, maxPricePerUnit);
      addTransaction(t,partner);
    }
    catch (NotEnoughQuantityException e){
      throw new LowOnStockException(e.getId(),e.getRequested(),
              e.getAvailable());
    }
    catch (UnsupportedOperationException e){}
  }

  public List<Transaction> processShowAcquisitionsByPartner
          (String partnerID) throws UndefinedPartnerKeyException{

    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null)
      throw new UndefinedPartnerKeyException(partnerID);

    List<Transaction> transactions = _transactions.get(p);
    List<Transaction> acquisitions = new LinkedList<>();

    if (transactions != null)
      for (Transaction t : transactions){
        t.acceptAcquisitionVisitor(
                new AcquisitionVisitor(this,acquisitions));
      }

    return acquisitions;
  }

  public List<String> processShowBreakdownsAndSalesByPartner
          (String partnerID) throws UndefinedPartnerKeyException{

    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null)
      throw new UndefinedPartnerKeyException(partnerID);

    List<Transaction> transactions = _transactions.get(p);
    List<String> acquisitions = new LinkedList<>();

    if (transactions != null)
      for (Transaction t : transactions){
        t.acceptSalesAndBreakDownVisitor(new
                SalesAndBreakDownVisitor(this,acquisitions,
                _date));
      }

    return acquisitions;
  }



  public void processRegisterAcquisition(String partnerID,
                                         String productID,
                                         double price, int amount)
          throws UndefinedPartnerKeyException{
    updateProductInfo(productID,price,amount);
    processRegisterAcquisitionNewProduct(partnerID,productID,price,amount);
  }

  public void processRegisterAcquisitionNewProduct(String partnerID,
                                                   String productID,
                                                   double price,
                                                   int amount) throws
          UndefinedPartnerKeyException{
    Partner partner = _partners.get(partnerID.toLowerCase());
    Product product = _products.get(productID);

    if (partner == null)
      throw new UndefinedPartnerKeyException(partnerID);
    Acquisition a = new Acquisition(getTransactionsID(),amount,
            price*amount,partner,product);
    a.setPaymentDate(_date);

    addTransaction(a,partner);
    Batch b = new Batch(partner,product,getBatchesID(),amount,price);
    incBatchesID();
    partner.addValueBought(price*amount);
    addBatch(product,b);

    a.pay(_date);
    _availableBalance -= a.getFinalPrice();
    _accountingBalance -= a.getFinalPrice();
  }

  public void processReceivePayment(int transactionID) throws
          UndefinedTransactionKeyException {

    Transaction t = findTransaction(transactionID);
    if (t == null) throw new
            UndefinedTransactionKeyException(transactionID);

    t.pay(_date);

    Partner p = t.getAssociatedPartner();

    _availableBalance += t.getFinalPrice();
  }

  public String processShowTransaction(int transactionID)
          throws UndefinedTransactionKeyException {

    Transaction t = findTransaction(transactionID);
    if (t == null) throw new
            UndefinedTransactionKeyException(transactionID);

    return t.displayTransaction(_date);
  }

  public List<Transaction> processLookupPaymentsByPartner
          (String partnerID) throws UndefinedPartnerKeyException{

    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null) throw new UndefinedPartnerKeyException(partnerID);

    List<Transaction> specificTransactions = new ArrayList<>();
    List<Transaction> allTransactions = _transactions.get(p);

    if (allTransactions != null)
      for (Transaction t : allTransactions){
        if (t.isPaid())
          specificTransactions.add(t);
      }

    return specificTransactions;
  }

  public void processRegisterBreakdownTransaction(String partnerID,
                                                  String productID,
                                                  int amount) throws
          UndefinedProductKeyException, UndefinedPartnerKeyException,
          LowOnStockException {

    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null) throw new UndefinedPartnerKeyException(partnerID);

    Product pr = _products.get(productID);
    if (pr == null) throw new UndefinedProductKeyException(productID);

    if (pr.getStock() < amount)
      throw new LowOnStockException(productID, amount, pr.getStock());

    try {
      Breakdown b = breakdownProduct(p, pr, amount);
      b.pay(_date);

      addTransaction(b, p);

      _availableBalance += b.getPaidPrice();
      _accountingBalance += b.getPaidPrice();
    }
    catch (UnsupportedOperationException e){}
  }

  public void processToggleProductNotifications(String partnerID,
                                                String productID)
          throws UndefinedProductKeyException,
          UndefinedPartnerKeyException {

    Partner p = _partners.get(partnerID.toLowerCase());
    if (p == null) throw new UndefinedPartnerKeyException(partnerID);

    Product pr = _products.get(productID);
    if (pr == null) throw new UndefinedProductKeyException(productID);

    if (pr.containsObserver(p)){
      pr.removeObserver(p);
    }
    else {
      pr.registerObserver(p);
    }
  }

  private void updateProductInfo(String productID, double price,
                                 int stock){

    Product product = _products.get(productID);
    if (product == null) return; // first time creating a product

    if (product.getStock() == 0)
      product.notifyObservers(new New(getDeliveryMethod(), product,
              price));

    updateProductStock(product, stock);

    if (price > product.getMaxPrice()) {
      product.setMaxPrice(price);
    }

    double minPrice = findLowestPriceOfProduct(product);

    if (price < minPrice) {
      product.setMinPrice(price);
      product.notifyObservers(new Bargain(getDeliveryMethod(),
              product, price));
    }

  }

  public void registerDerivedProduct(String productID, int stock,
                                     double price, double alpha,
                                     String recipe){
    Recipe recipe1 = componentsToRecipe(recipe);
    registerMProduct(productID,stock,price,alpha,recipe1);

  }


  /** Transforms a string with the ingredients and the respective
   *  quantities and turns it into a recipe.
   * @param components the components of the recipe and respective
   *                    quantity
   *  @return a recipe
   */
  private Recipe componentsToRecipe(String components){
    Recipe recipe = new Recipe();
    String[] splittedComponents = components.split("#");
    String[] pairProductQuantity;

    for(String pair: splittedComponents){
      pairProductQuantity = pair.split(":");
      recipe.addIngredient(_products.get(pairProductQuantity[0]),
              Integer.parseInt(pairProductQuantity[1]));
    }

    return recipe;
  }

  /**Registers a new simple product and alters its overall stock
   *               from 0 to the number of products.
   *  It also adds it to the collection of all products.
   * @param productID the id of the product.
   *  @param stock the number of elements of that product that exist
   *               overall.
   *  @param price the price.
   */

  public SimpleProduct registerSProduct(String productID, int stock,
                               double price){
    SimpleProduct product = new SimpleProduct(productID, price);
    product.changeStock(stock);
    _products.put(productID, product);
    for (Map.Entry<String,Partner> entry : _partners.entrySet())
      product.registerObserver(entry.getValue());
    return product;
  }

  /**Registers a new derived product and alters its overall stock
   *                from 0 to the number of products.
   *  It also adds it to the collection of all products.
   * @param productID the id of the product.
   *  @param stock the number of elements of that product that exist
   *               overall.
   *  @param price the price.
   *  @param alpha the aggravating factor associated.
   *  @param recipe the recipe associated with the product.
   */
  public DerivedProduct registerMProduct(String productID, int stock,
                               double price, double alpha,
                               Recipe recipe){
    DerivedProduct product = new DerivedProduct(productID, price,
            alpha, recipe);
    product.changeStock(stock);
    _products.put(productID, product);
    for (Map.Entry<String,Partner> entry : _partners.entrySet())
      product.registerObserver(entry.getValue());
    return product;
  }

  private void addBatch(Product product, Batch batch){
    TreeSet<Batch> batchSet = _batches.get(product);

    if (batchSet == null){
      batchSet = new TreeSet<>(new BatchPriceComparator());
      batchSet.add(batch);
      _batches.put(product,batchSet);
    }
    else
      batchSet.add(batch);
  }



  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  public void importFile(String txtfile) throws
          IOException, BadEntryException{
    BufferedReader in = new BufferedReader(new FileReader(txtfile));
    String line;
    String[] splitted;

    while ((line = in.readLine()) != null){
        splitted = line.split("\\|");
        try {
          switch (splitted[0]) {

            case "PARTNER" -> processRegisterPartner(splitted[1],
                    splitted[2], splitted[3]);
            case "BATCH_S" -> processRegisterSBatch(splitted[1],
                    splitted[2], Integer.parseInt(splitted[3]),
                    Integer.parseInt(splitted[4]));
            case "BATCH_M" -> processRegisterMBatch(splitted[1],
                    splitted[2], Integer.parseInt(splitted[3]),
                    Integer.parseInt(splitted[4]),
                    Double.parseDouble(splitted[5]),
                    componentsToRecipe(splitted[6]));

          }
        }
        catch (UndefinedPartnerKeyException e){
          e.printStackTrace();
        } catch (SamePartnerKeyException e){
          e.printStackTrace();
        }
    }
  }
  private void addTransaction(Transaction t, Partner p){
    List<Transaction> partnerTransactions = _transactions.get(p);
    if (partnerTransactions == null){
      partnerTransactions = new ArrayList<>();
      partnerTransactions.add(t);
      _transactions.put(p,partnerTransactions);
    }
    else
      partnerTransactions.add(t);
    incTransactionsID();
  }

  public double aggregate(DerivedProduct product){
    double price = 0;
    List<Ingredient> ingredients = product.getRecipe().getIngredients();
    for (Ingredient i : ingredients){
      Product p = i.getProduct();
      int amount = i.getAmount();
      price += sellBatches(p,amount);
      }
    price*=(1+product.getAlpha());
    return price;
    }


  private void updateProductStock(Product product, int slack){
    product.changeStock(slack);
  }

  private void updateProductMaxPrice(Product product, double maxPrice){
    if (maxPrice > product.getMaxPrice()){
      product.setMaxPrice(maxPrice);
    }
  }

  public void addToList(Transaction t, List<Transaction> l){
    l.add(t);
  }

  public void addToList(String str, List<String> l){
    l.add(str);
  }
  
  public boolean productExists(String productID){
    return _products.containsKey(productID);
  }

  private double findLowestPriceOfProduct(Product p){

    TreeSet<Batch> batches = _batches.get(p);
    if (batches != null)
      for (Batch b : batches){
        if (!b.soldOutBatch())
          return b.getPrice();
      }
    return p.getMaxPrice();
  }

  public double sellBatches(Product p, int amount){

    int remaining = amount;
    if (p.getStock() <= amount)
      p.changeStock(-p.getStock());
    else
      p.changeStock(-amount);
    double totalPrice = 0; // Total price to be paid
    TreeSet<Batch> batches = _batches.get(p);

    for (Batch b : batches){
      if (remaining == 0) break;
      if (!b.soldOutBatch()){

        if (remaining > b.getAmount()){ // The batch isn't
          // enough to cover remaining amount
          totalPrice += b.getAmount() * b.getPrice();
          remaining -= b.getAmount();
          b.sellout();
        }

        else { // The batch can cover the remaining amount
          totalPrice += remaining * b.getPrice();
          b.reduceAmount(remaining);
          remaining = 0;
        }

      }
    }

    return totalPrice;
  }



  public double createdBatchesFromDerivedProduct(DerivedProduct pr,
                                                 Partner p,
                                                 int derivedPrAmount){

    double totalPrice = 0;
    List<Ingredient> ingredients = pr.getRecipe().getIngredients();

    for (Ingredient i : ingredients){
      Product currPr = i.getProduct();
      double pricePerProduct = findLowestPriceOfProduct(currPr);

      Batch b = new Batch(p, currPr, getBatchesID(),
              i.getAmount() * derivedPrAmount, pricePerProduct);

      updateProductInfo(currPr.getId(), pricePerProduct,
              i.getAmount() * derivedPrAmount);
      addBatch(currPr, b);
      incBatchesID();

      totalPrice += pricePerProduct * i.getAmount() * derivedPrAmount;
    }

    return totalPrice;
  }


  public Breakdown breakdownProduct(Partner p, Product pr, int amount)
          throws UnsupportedOperationException{
    // breakdowns the product and adds all the components to the warehouse
    return pr.acceptBreakdown(new BreakdownVisitor(this),
            p, amount);
  }



  public String craftExtendedRecipe(DerivedProduct pr,
                                    int breakdownAmount){

    String extendedRecipe = "";
    List<Ingredient> ingredients = pr.getRecipe().getIngredients();

    for (Ingredient i : ingredients){
      String productID = i.getProduct().getId();
      extendedRecipe += productID + ":" +
              i.getAmount()*breakdownAmount + ":" +
              Math.round(findLowestPriceOfProduct(i.getProduct())*
                      i.getAmount()*breakdownAmount) + "#";
    }
    return extendedRecipe.substring(0, extendedRecipe.length()-1);
  }
}



