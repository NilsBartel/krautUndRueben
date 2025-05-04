import { useNavigate, useLocation } from 'react-router-dom';
import "./Header.css"
import ShoppingCartModal from './ShoppingCartModal';
import { createPortal } from 'react-dom';
import { useState, useEffect } from 'react';
import { hasToken } from '../lib/token';

function Header() {
  const navigate = useNavigate();
  const location = useLocation();
  const [cartOpen, setCartOpen] = useState(false);
  const [itemCount, setItemCount] = useState(0);

  const handleAccountClick = () => {
    hasToken() ?
      navigate('/account') : navigate('/login');
  };

  useEffect(() => {
    updateCount();

    window.addEventListener('storage', updateCount)

    return () => {
      window.removeEventListener('storage', updateCount)
    }
  }, []);

  function updateCount() {
    setItemCount(JSON.parse(localStorage.getItem("cart")).items.length)
  }


  return (
    <>
      <div className="container-fluid p-5 text-white text-center header" >
        <h1 onClick={() => navigate('/')}>KRAUT UND RÜBEN</h1>
        <div>
          <button
            className="heeaderButton"
            id="cartButton"
            aria-label="Shopping Cart"
            onClick={() => setCartOpen(true)}
          >
            {itemCount > 0 ? <div className='cartItemCount'><p>{itemCount}</p></div> : <></>}

            <img src='shoppingcart.png' alt='Shopping Cart' className='header-img' />
          </button>
          <button
            className="heeaderButton"
            id="accountbutton"
            aria-label="Account"
            onClick={handleAccountClick}
          >
            <img src="account.png" alt="Account" className="header-img" />
          </button>
        </div>
      </div>

      <ShoppingCartModal isOpen={cartOpen} onClose={() => { setCartOpen(false) }} />,


    </>
  );
}

export default Header;
