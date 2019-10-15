// Testing dataset for tanK

// Ingredients and recipes. All foods have caloric density (calories per gram)

// Raw Ingredients
CREATE (carrot:Ingredient {name: 'carrot', caloric_density: 0.4135})
CREATE (onion:Ingredient {name: 'onion', caloric_density: 0.40})
CREATE (celery:Ingredient {name: 'celery', caloric_density: 0.1601})
CREATE (olive_oil:Ingredient {name: 'olive oil', caloric_density: 8.84})
CREATE (ground_beef:Ingredient {name: 'ground beef', caloric_density: 3.32})
CREATE (tomato:Ingredient {name: 'tomato', caloric_density: 0.1769})
CREATE (garlic:Ingredient {name: 'tomato', caloric_density: 1.49})
CREATE (sugar:Ingredient {name: 'sugar', caloric_density: 3.89})
CREATE (red_wine:Ingredient {name: 'red wine', caloric_density: 1.25})
CREATE (broth:Ingredient {name: 'broth', caloric_density: 0.03})
CREATE (spaghetti:Ingredient {name: 'spaghetti', caloric_density: 1.58})
CREATE (salt:Ingredient {name: 'salt', caloric_density: 0})
CREATE (water:Ingredient {name: 'water', caloric_density: 0})
CREATE (lemon:Ingredient {name: 'lemon', caloric_density: 0.29})
CREATE (flour:Ingredient {name: 'flour', caloric_density: 3.64})
CREATE (cream:Ingredient {name: 'cream', caloric_density: 1.96})
CREATE (cabbage:Ingredient {name: 'cabbage', caloric_density: 0.25})

// Ingredient Recipes
CREATE (soffritto:Recipe {name: 'soffritto', description: 'stir ingredients over medium heat'})
CREATE (soffritto)-[:MADE_FROM {grams: 200}]->(onion)
CREATE (soffritto)-[:MADE_FROM {grams: 100}]->(celery)
CREATE (soffritto)-[:MADE_FROM {grams: 100}]->(carrot)
CREATE (soffritto)-[:MADE_FROM {grams: 15}]->(olive_oil)

CREATE (passata:Recipe {name: 'passata', description: 'fry, boil and purée'})
CREATE (passata)-[:MADE_FROM {grams: 33}]->(garlic)
CREATE (passata)-[:MADE_FROM {grams: 1000}]->(tomato)
CREATE (passata)-[:MADE_FROM {grams: 14}]->(olive_oil)
CREATE (passata)-[:MADE_FROM {grams: 4.76}]->(sugar)

CREATE (cooked_spaghetti:Recipe {name: 'cooked spaghetti', description: 'cook pasta in water'})
CREATE (cooked_spaghetti)-[:MADE_FROM {grams: 100}]->(spaghetti)
CREATE (cooked_spaghetti)-[:MADE_FROM {grams: 200}]->(water)
CREATE (cooked_spaghetti)-[:MADE_FROM {grams: 5}]->(salt)

CREATE(spaghetti_bolognese:Recipe {name:        'spaghetti bolognese',
                                   description: 'make soffritto, add wine and reduce, add ground beef and brow, add passata and broth and reduce to a pasta sauce, serve with fresh cooked pasta'})
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 200}]->(soffritto)
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 300}]->(ground_beef)
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 50}]->(red_wine)
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 400}]->(broth)
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 250}]->(passata)
CREATE (spaghetti_bolognese)-[:MADE_FROM {grams: 450}]->(cooked_spaghetti)

CREATE(lemonade:Recipe {name:        'lemonade',
                        description: 'heat water and dissolve sugar, mix in lemon, add more water if lemonade is too strong and chill'})
CREATE (lemonade)-[:MADE_FROM {grams: 100}]->(lemon)
CREATE (lemonade)-[:MADE_FROM {grams: 100}]->(water)
CREATE (lemonade)-[:MADE_FROM {grams: 50}]->(sugar)

CREATE(stuffed_cabbage:Recipe {name:        'simple stuffed cabbage',
                               description: 'cook cabbage leaves until soft, brown beef and onions, roll cabbage leaves around beef, put in the bottom of a large pan, cover with passata and bake at a low temperature'})
CREATE (stuffed_cabbage)-[:MADE_FROM {grams: 500}]->(ground_beef)
CREATE (stuffed_cabbage)-[:MADE_FROM {grams: 1000}]->(passata)
CREATE (stuffed_cabbage)-[:MADE_FROM {grams: 400}]->(onion)
