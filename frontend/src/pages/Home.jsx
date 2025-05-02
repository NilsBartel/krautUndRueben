// src/components/Home.js
import Header from '../components/Header';
import '../App.css';
import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();




  const boxData = [
    { name: 'Veggi Box', link: "/veggi" },
    { name: 'Vegan Box', link: "/vegan" },
    { name: 'Gym Bro Box', link: "/gymbro" },
    { name: 'Mixed Food Box', link: "/mixed" },
  ];



  return (
    <>
      <Header />
      <div className="container mt-5 text-center">

        <div className="customised-box mb-5 p-4 rounded shadow ">
          <img
            src="boxui.png"
            alt="Customised Box"
            style={{ width: '100px', marginBottom: '15px' }}
          />
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
                  src="boxui.png"
                  className="card-img-top"
                  alt={box.name}
                  style={{ height: '150px', objectFit: 'contain' }}
                />

                <div className="card-body">
                  <h5 className="card-title">{box.name}</h5>
                  <button className="btn btn-primary" onClick={() => navigate(box.link)}>
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
