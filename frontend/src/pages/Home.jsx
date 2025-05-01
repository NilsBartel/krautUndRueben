// src/components/Home.js
import Header from '../components/Header';
import '../App.css'; // Falls du allgemeines CSS dort hast
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();

  const boxData = [
    { name: 'Veggi Box', image: 'veggi.png' },
    { name: 'Vegan Box', image: 'vegan.png' },
    { name: 'Gym Bro Box', image: 'gym.png' },
    { name: 'Mixed Food Box', image: 'mixed.png' },
  ];

  return (
    <>
      <Header />
      <div className="container mt-5 text-center">
        {/* Customised Box separat und präsent */}
        <div className="customised-box mb-5 p-4 rounded shadow text-white">
          <h2>Customised Box</h2>
          <p>Stelle dir deine persönliche Box zusammen!</p>
          <button className="btn btn-warning mt-3" onClick={() => navigate('/customised')}>
            Jetzt anpassen
          </button>
        </div>

        {/* Andere Boxen als Grid */}
        <div className="row">
          {boxData.map((box, index) => (
            <div className="col-md-3 mb-4" key={index}>
              <div className="card h-100 text-center">
                <img
                  src={box.image}
                  className="card-img-top"
                  alt={box.name}
                  style={{ height: '150px', objectFit: 'cover' }}
                />
                <div className="card-body">
                  <h5 className="card-title">{box.name}</h5>
                  <button className="btn btn-primary" onClick={() => alert(`${box.name} gewählt!`)}>
                    Auswählen
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

export default Home;
