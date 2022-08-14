package ggc;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable {
    private List<Ingredient> _recipe;

    public Recipe() {
        _recipe = new ArrayList<>();
    }


    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        for (Ingredient i: _recipe) {
            products.add(i.getProduct());
        }
        return products;
    }
    public List<Ingredient> getIngredients(){
        return _recipe;
    }


    public void addIngredient(Product product, int quantity) {
        _recipe.add(new Ingredient(product,quantity));
    }

    @Override
    public String toString(){
        String out = "";
        for (Ingredient i : _recipe){
            out += i.getProduct().getId() + ":" + i.getAmount() + "#";
        }
        return out.substring(0, out.length() - 1);
    }


}
