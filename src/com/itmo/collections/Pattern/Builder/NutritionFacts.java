package com.itmo.collections.Pattern.Builder;

public class NutritionFacts {
    private final int servings;
    private final int calories;
    private final int fat;

    public static class Builder {
        // Обязательные параметры
        private final int servings;
        // Дополнительные параметры - инициализируются значениями по умолчанию
        private int calories = 0;
        private int fat = 0;
        public Builder(int servings) {
            this.servings = servings;
        }
        public Builder calories(int val) {
            calories = val;
            return this;
        }
        public Builder fat(int val) {
            fat = val;
            return this;
        }
        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }
    private NutritionFacts(Builder builder) {
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
    }

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts
                .Builder(8)
                .calories(100)
                .build();
        NutritionFacts beaf = new NutritionFacts
                .Builder(10)
                .calories(57)
                .fat(38)
                .build();
    }
}
