package ggc;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable {
    private List<Ingredient> _recipe;

    public Recipe() {
        _recipe = new ArrayList<>();
    }


    public void addIngredient(Product product, int quantity) {
        _recipe.add(new Ingredient(product,quantity));
    }

    /* //P
    *  Cria uma lista de produtos;
    *  Ordena-a pelo nome dos produtos (ordem natural);
    *  Constroi a string de saida com a os produtos e a quantidade associada;
    *  Retira o último "#";
    * */


    @Override
    public String toString(){
        String out = "";
        for (Ingredient i : _recipe){
            out += i.getProduct().getId() + ":" + i.getAmount() + "#";
        }
        return out.substring(0, out.length() - 1);
    }


    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        for (Ingredient i: _recipe) {
            products.add(i.getProduct());
        }
        return products;
    }


    public List<Integer> getQuantities(){
        List<Integer> quantities = new ArrayList<>();
        for (Ingredient i: _recipe) {
            quantities.add(i.getAmount());
        }
        return quantities;
    }  //Fazer metodo melhor

    public List<Ingredient> getIngredients(){
        return _recipe;
    }
}
