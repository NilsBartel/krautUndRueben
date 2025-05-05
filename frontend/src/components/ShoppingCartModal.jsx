import { useEffect, useState, useRef } from "react";
import "./ShoppingCartModal.css";

function ShoppingCartModal(props) {
    const [cartItems, setCartItems] = useState([]);
    const [isOpen, setOpen] = useState(props.isOpen)
    const dialog = useRef(null)

    useEffect(() => {
        updateCart();

        window.addEventListener('storage', updateCart)

        return () => {
            window.removeEventListener('storage', updateCart)
        }
    }, []);

    useEffect(() => {
        setOpen(props.isOpen);
        props.isOpen ? dialog.current?.showModal() : dialog.current.close();
    }, [props.isOpen]);

    function removeItem(key) {
        const other = cartItems.filter((item) => item.key != key);
        setCartItems(other);

        localStorage.setItem("cart", JSON.stringify({
            items: other
        }));
        window.dispatchEvent(new Event('storage'));
    }

    function updateCart() {
        const storedCart = localStorage.getItem("cart");
        if (storedCart) {
            const parsed = JSON.parse(storedCart);
            setCartItems(parsed.items || []);
        } else {
            localStorage.setItem("cart", JSON.stringify({ items: [] }))
            setCartItems([]);
        }
    }

    function clearCart() {
        localStorage.setItem("cart", JSON.stringify({ items: [] }))
        setCartItems([]);

        window.dispatchEvent(new Event('storage'))
    }

    function handleOrder() {
        clearCart();
        dialog.current.close();
        props.onClose();
        alert("Danke fürs Bestellen!");
    }

    return (
        <>
            <dialog closedby="none" ref={dialog} className="cartModal" onClose={() => {
                props.onClose();
            }}>
                <button onClick={() => dialog.current.close()}>close</button>
                <h1>Einkaufswagen</h1>
                {cartItems.length > 0 ? (
                    cartItems.map((item, index) => (
                        <div className="cartItem" key={index}>
                            <p>{item.name}</p>
                            <button onClick={() => removeItem(item.key)}>Entfernen</button>
                        </div>
                    ))
                ) : (
                    <p>Dein Warenkorb ist leer.</p>
                )}
                {cartItems.length > 0 && (
                    <button onClick={handleOrder} className="orderButton">
                        Bestellen
                    </button>
                )}
            </dialog>
        </>
    );
}

export default ShoppingCartModal;
