package tutorial.zeldaboy111.factory;

public class FoodFactory {

    public Food getFood(String foodType) throws NullPointerException {

        if(foodType == null) return null;

        Food f;

        if (foodType.equalsIgnoreCase("bread")) {
            f = new ChocolateBread();
        } else if (foodType.equalsIgnoreCase("oreo")) {
            f = new Oreo();
        }else if (foodType.equalsIgnoreCase("honey bread")) {
            f = new HoneyBread();
        } else {
            throw new NullPointerException();
        }

        return f;
    }

}
