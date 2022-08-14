package ggc;
import java.util.List;
import ggc.exceptions.NotEnoughQuantityException;

public class DerivedProduct extends Product {
    private double _alpha;
    private Recipe _recipe;

    public DerivedProduct(String id, double maxPrice,
                          double alpha, Recipe recipe) {
        super(id, maxPrice);
        _alpha = alpha;
        _recipe = recipe;
        setOffsetN(5);
    }

    public double getAlpha(){
        return _alpha;
    }

    public Breakdown acceptBreakdown(ProductVisitor visitor, Partner p, int amount){
        return visitor.proceedWithBreakdown(this, p, amount);
    }

    public Recipe getRecipe(){
        return _recipe;
    }

    public boolean checkAvailability(int amount)
                                    throws NotEnoughQuantityException{
        if (getStock() >= amount)
            return true;

        int amountLeft = amount - getStock();
        List<Ingredient> ingredients = _recipe.getIngredients();

        for (Ingredient i : ingredients)
            if(i.getProduct().getStock() < amountLeft * i.getAmount())
                throw new NotEnoughQuantityException(i.getProduct(),
                                            amountLeft *i.getAmount(),
                                           i.getProduct().getStock());
        return true;
    }

    public double acceptSale(ProductVisitor visitor){
        return visitor.aggregate(this);
    }


    @Override
    public String toString(){
        return super.toString() + "|" +
                _alpha + "|" +
                _recipe;
    }

}


