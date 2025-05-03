import { useNavigate, useLocation } from 'react-router-dom';
import "./Header.css"
import ShoppingCartModal from './ShoppingCartModal';
import { createPortal } from 'react-dom';
import { useState } from 'react';

function Header() {
  const navigate = useNavigate();
  const location = useLocation();
  const [cartOpen, setCartOpen] = useState(false);

  const handleAccountClick = () => {
    navigate('/account');
  };

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
