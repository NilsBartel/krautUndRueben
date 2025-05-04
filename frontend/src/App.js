import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import Home from './pages/Home';
import Login from './pages/Login';
import Customized from './pages/Customised';
import Veggi from './pages/Veggi';
import Vegan from './pages/Vegan';
import Gymbro from './pages/gymbro';
import Mixed from './pages/mixed';
import Account from './pages/Account';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path='/account' element={<Account />} />
        <Route path='/customised' element={<Customized />} />
        <Route path='/veggi' element={<Veggi />} />
        <Route path='/vegan' element={<Vegan />} />
        <Route path='/gymbro' element={<Gymbro />} />
        <Route path='/mixed' element={<Mixed />} />
      </Routes>
    </Router>
  );
}

export default App;
