import { useState} from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import axios from "axios";
import { useCookies } from 'react-cookie';

import './css/Modals.css';

function DeleteModal(props) {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);

  function deleteCertificate(token){

    let config = {
      headers: {
        Authorization: 'Bearer ' + token
      }
    };

    const deleteCert = async() => {
      axios
        .delete("certificate/" + props.id, config)
        .then(() => {
          props.onDeleteCertificate(false, 0);
        })        
        .catch((error) => {
          if(error.response.data.errorMessage != null)
            props.onMessageChange(error.response.data.errorMessage);
          else{
            handleError(error, config);
          }  
        });
    }
    deleteCert();    
  }

  function handleError(error, config){
    if(error.response.data.errorMessage != null)
      props.onMessageChange(error.response.data.errorMessage);
    else{
      if(error.response.data.error.includes('JWT expired')) {
        refreshToken(config);     
      }
      else props.onMessageChange(error.response.data.error);
    }    
  }

  function refreshToken(config){
    axios
    .post("refresh", null, config)
    .then(response => {
      deleteCertificate(response.data.token);
    })
    .catch((error) => {
      if(error.response.data.errorMessage != null)
        props.onMessageChange(error.response.data.errorMessage);
      else
        props.onMessageChange(error.response.data.error);
    });                      
  }

  return (
    <Modal {...props} centered>
      <Modal.Header className="modal-title">
        <Modal.Title id="delete-modal">
          Delete confirmation
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p class="inner-text">
          Do you really want to delete certificate with id = {props.id}?
        </p>
      </Modal.Body>
      <Modal.Footer>
        <Button className="submit-button" onClick={() => deleteCertificate(cookies.token)}>Yes</Button>
        <Button className="cancel-button" onClick={() => props.onDeleteCertificate(false, 0)}>Close</Button>
      </Modal.Footer>
    </Modal>
  );  
}

export default DeleteModal;