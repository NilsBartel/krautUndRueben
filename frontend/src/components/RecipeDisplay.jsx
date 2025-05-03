import "./RecipeDisplay.css"

function RecipieDisplay({ recipes, hidden, boxData }) {
    function addToCart() {

        let cart = JSON.parse(localStorage.getItem("cart"));
        if (cart == null) {
            cart = { items: [] };
        }

        cart.items.push({
            name: boxData.name,
            id: boxData.id,
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
                <li key={index} >
                    <ul>{recipe.name}</ul>
                </li>
            ))}

            <button onClick={addToCart}>In den Warenkorb</button>
        </div>
    );
}

export default RecipieDisplay;