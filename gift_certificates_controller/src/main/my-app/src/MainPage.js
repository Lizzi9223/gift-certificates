import React, {useState, useEffect, useRef} from 'react';
import { useCookies } from 'react-cookie';

import Certificates from './Certificates';
import DeleteModal from './DeleteModal';
import AddOrEditCertificate from './AddOrEditCertificate'

import './css/MainPage.css';

function MainPage({mainPageRef}){
    const[errorMessage, setErrorMessage] = useState('');
    const[searchString, setSearchString] = useState('');
    const[isErrorMessageShown, setIsErrorMessageShown] = useState(false);    
    const[showDeleteModel, setShowDeleteModel] = useState(false);
    const[showAddEditModel, setShowAddEditModel] = useState(false);
    const[editCertificate, setEditCertificate] = useState(false);
    const[chosenCertId, setChosenCertId] = useState(0);
    const[searchParams, setSearchParams] = useState({name:'', tags: []});
    const certificatesRef = useRef();
    const [cookies, setCookie, removeCookie] = useCookies(["page"]);
    let name = '';
    let tags = [];

    useEffect(() => {
      mainPageRef.current = addOrEditCertificate;
    }, []);

    const onMessageChange = (message) => {
        setErrorMessage(message);
        setIsErrorMessageShown(true);
    }

    const closeErrorMessage = () => {
        setErrorMessage("");
        setIsErrorMessageShown(false);
    }

    const onSearchInputChange = (e) => {
        const value = e.target && e.target.value;
        setSearchString(value);
    };  

    useEffect(() => {
      certificatesRef.current.getCertificatesByPagination(cookies.page);
    }, [searchParams]);

    const doSearch = (e) => {
        e.preventDefault();

        const reg = RegExp('(\\w+)\|\#\\((\.\*\?)\\)','g');
        const params = searchString.matchAll(reg);

        Array.from(params, p => {
            var param = p[0];
            if(param.includes('#')){
                var tag = param.replace('#','').replace('(','').replace(')','');
                tags.push(tag);
            } else {
                name = param;  
            }
        });
      
        setCookie("page", 0, { path: '/' });
        setSearchParams({name:name, tags:tags});
    }

    const deleteCertificate = (show, id) => {   
        setShowDeleteModel(show);
        setChosenCertId(id);
        if(!show){
            certificatesRef.current.getCertificatesByPagination(cookies.page);
        }
    }

    const addOrEditCertificate = (editCertificate, show, id) => {
        setEditCertificate(editCertificate);
        setShowAddEditModel(show);
        setChosenCertId(id);
        if(!show){
          certificatesRef.current.getCertificatesByPagination(cookies.page);
        }
    }

    return(
      <div class="main-page">    
        <div className="error-message" style={{ display: isErrorMessageShown ? "flex" : "none" }}>
          <div className="status">
            <div className="image"><img src={require('./images/remove.png')} /></div>
            <div>{errorMessage}</div>
          </div>
          <div className="close" onClick={closeErrorMessage}><img src={require('./images/close.png')} /></div>
        </div>

        <div className="search" style={{ marginTop: isErrorMessageShown ? "1%" : "6%" }}>
          <input type="text" placeholder="Search..." value={searchString} onChange={onSearchInputChange}/>
          <input type="submit" value="Got" onClick={doSearch} />
        </div>

        <Certificates 
          searchParams={searchParams} 
          errorMessage={errorMessage} 
          onMessageChange={onMessageChange} 
          onDeleteCertificate={deleteCertificate}
          onAddOrEditCertificate={addOrEditCertificate}
          ref={certificatesRef} />

        <DeleteModal 
          show={showDeleteModel} 
          id={chosenCertId}
          onDeleteCertificate={deleteCertificate}
          onMessageChange={onMessageChange}
          errorMessage={errorMessage} />

        <AddOrEditCertificate
          editCertificate={editCertificate}
          show={showAddEditModel} 
          id={chosenCertId}
          onAddOrEditCertificate={addOrEditCertificate}
          onMessageChange={onMessageChange}
          errorMessage={errorMessage} />
      </div>
    )
}

export default MainPage;