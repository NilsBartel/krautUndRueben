import "./RecipeDisplay.css"

function RecipieDisplay({ recipes, hidden, boxData }) {
    console.log(recipes);


    function addToCart() {

        let cart = JSON.parse(localStorage.getItem("cart"));
        if (cart == null) {
            cart = { items: [] };
        }

        cart.items.push({
            name: boxData.name,
            typ: boxData.id,
            rezepte: recipes.map((rezept) => rezept.name),
            key: Date.now()
        });

        localStorage.removeItem("cart");
        localStorage.setItem("cart", JSON.stringify(cart));
        window.dispatchEvent(new Event('storage'))
    }


    if (hidden) {
        return <></>;
    }

    if (recipes.length == 0) {
        return (
            <>
                <h1>Keine Rezepte gefunden.</h1>
            </>
        );
    }

    return (
        <div className="recipeContainer">
            <h1>Rezepte: </h1>
            {recipes.map((recipe, index) => (
                <div key={index} >
                    <h2>{recipe.name}</h2>
                    <ul>
                        {recipe.zutaten.map((zutat, index) => (
                            <li key={index}>{`${zutat.menge}${zutat.einheit} ${zutat.name}`}</li>
                        ))}
                    </ul>
                </div>
            ))}

            <button onClick={addToCart}>In den Warenkorb</button>
        </div>
    );
}

export default RecipieDisplay;