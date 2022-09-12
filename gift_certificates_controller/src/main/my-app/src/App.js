import { Routes, Route, useNavigate } from "react-router-dom"
import { useRef } from "react";

import LoginForm from "./LoginForm"
import MainPage from "./MainPage"
import Navbar from './Navbar';

function App(props) {
    const navigate = useNavigate();
    const mainPageRef = useRef(null);

    const path = window.location.pathname;

    return (
        <div>
            <Navbar showButtons={path == '/login' ? false : true} navigate={navigate} mainPageRef={mainPageRef} />
            <div className="app">        
            <Routes>
                <Route path="/login" element={<LoginForm navigate={navigate}  /> } />
                <Route path="/certificates" element={<MainPage mainPageRef={mainPageRef} /> }  />
            </Routes>
            </div>
        </div>
    )
}

export default App