import React from "react";
import { useCookies } from 'react-cookie';

import "./css/Navbar.css"

function Navbar(props){
    const [cookies, setCookie, removeCookie] = useCookies(["token","login","page"]);

    const logout = () => {
        removeCookie("token");
        removeCookie("login");
        removeCookie("page");
        props.navigate('/login');
    }
    
    return(
        <header>
            <nav className="navbar">
                <div className="left">
                    <a href="#">Admin UI</a> 
                    <button className="buttons" 
                        onClick={() => props.mainPageRef.current(false, true, 0)}
                        style={{ display: (props.showButtons && cookies.token ) ? "inline-block" : "none" }}>
                        Add new
                    </button>
                </div>
                <div className="right" style={{ display: (props.showButtons && cookies.token ) 
                                                                            ? "flex" : "none" }}>
                    <button className="square"><img src="#"/></button>
                    <button>{cookies.login}</button>
                    <button onClick={logout}>Logout</button>
                </div>                
            </nav>
        </header>
    )   
}

export default Navbar;