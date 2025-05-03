import { useEffect, useState, useRef } from "react";
import "./ShoppingCartModal.css"


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
        const other = cartItems.filter((item) => item.key != key)
        setCartItems(other);
        console.log(other);



        localStorage.removeItem("cart");
        localStorage.setItem("cart", JSON.stringify({
            items: other
        }));
        window.dispatchEvent(new Event('storage'))
    }

    function updateCart() {
        setCartItems(JSON.parse(localStorage.getItem("cart")).items)
    }

    return (
        <>
            <dialog closedby="none" ref={dialog} className="cartModal" onClose={() => {
                props.onClose();
            }}>
                <button onClick={() => {
                    dialog.current.close();
                }} >close</button>
                <h1>Einkaufswagen</h1>
                {cartItems.map((item, index) => (
                    <div className="cartItem" key={index}>
                        <p>{item.name}</p>
                        <button onClick={() => removeItem(item.key)}>Entfernen</button>
                    </div>
                ))}
            </dialog>
        </>
    );
}


export default ShoppingCartModal;