import React from 'react';
import './App.css';
import SignIn from './components/SignIn/SignIn';




function App(props) {
  const [user] = useAuthState(auth);


  return (
    <div className="App">
      <header className="App-header">
        
      </header>
      <section>

      </section>
    </div>
  );
}

export default App;
