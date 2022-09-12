import React, { useState, useEffect } from 'react';
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import axios from "axios";
import { WithContext as ReactTags } from 'react-tag-input';
import { useCookies } from 'react-cookie';

import './css/Modals.css';
import './css/ReactTags.css';

function AddOrEditCertificate(props) {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [duration, setDuration] = useState();
  const [price, setPrice] = useState();
  const [tags, setTags] = useState([]);
  const [suggestions, setSuggestions] = useState([]);
  const [dataLoaded, setDataLoaded] = useState(false);
  const [titleValid, setTitleValid] = useState(true);
  const [descriptionValid, setDescriptionValid] = useState(true);
  const [durationValid, setDurationValid] = useState(true);
  const [priceValid, setPriceValid] = useState(true);
  const [tagsValid, setTagsValid] = useState(true);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);

  useEffect(() => {
    if(props.editCertificate && !dataLoaded){
      axios
        .get("certificate/" + props.id)
        .then(response => response.data)
        .then(data => {
          setTitle(data.name);
          setDescription(data.description);
          setDuration(data.duration);
          setPrice(data.price);
          let tags = [];
          data.tags.map((tag) => {
            let tag_ = {
              id: tag.name,
              text: tag.name
            };
            tags.push(tag_);          
          });
          setTags(tags);
          setDataLoaded(true);
        })
      .catch((error) => {
        if(error.response.data.errorMessage == null || error.response.data.errorMessage === "")
          props.onMessageChange(error.response.data.errorMessage);
        else props.onMessageChange(error.response.data.error);
      })
    }
  });

  useEffect(() => {
    axios
      .get("tag?page=0&size=2000")
      .then(response => response.data)
      .then(data => {
        let tags = [];
        data.map((tag) => {
          let tag_ = {
            id: tag.name,
            text: tag.name
          };
          tags.push(tag_);                 
        });
        setSuggestions(tags);
      }) 
    .catch((error) => {
        if(error.response.data.errorMessage == null || error.response.data.errorMessage === "")
          props.onMessageChange(error.response.data.errorMessage);
        else props.onMessageChange(error.response.data.error);
      });
  }, []);
  
  const KeyCodes = {
    comma: 188,
    enter: 13,
  };
  
  const delimiters = [KeyCodes.comma, KeyCodes.enter];

  function addOrEditCertificate(token){
    let tags_ = [];
    tags.map((tag) => {
      let tag_ = {
        name: tag.text
      };
      tags_.push(tag_);    
    });

    if(!validateInputs()) return;

    let config = {
      headers: {
        Authorization: 'Bearer ' + token
      }
    };

    let cert = {};
    if(tags_.length > 0){
      cert = {
        name: title,
        description: description,
        price: price,
        duration: duration,
        tags: tags_    
      }
    } else{
      cert = {
        name: title,
        description: description,
        price: price,
        duration: duration
      }
    }

    if(props.editCertificate){
      const update = async() => {
        await axios
        .put("certificate/" + props.id, 
              cert,
              config)
        .then(() => {
          setStates();
        })
        .catch((error) => {
          handleError(error, config);
        });        
      }      
      update();      
    }else{      
      const create = async()=> {
        await axios
        .post("certificate/", 
              cert,
              config)
        .then(() => {
          setStates();
        })
        .catch((error) => {
          handleError(error, config);
        });
      }
      create();
    }
  }

  function setStates(){
    setDataLoaded(false); 
    setTitle('');
    setDescription('');
    setDuration();
    setPrice();
    setTags([]);
    props.onAddOrEditCertificate(false, false, 0);
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
      addOrEditCertificate(response.data.token);
    })
    .catch((error) => {
      if(error.response.data.errorMessage != null)
        props.onMessageChange(error.response.data.errorMessage);
      else
        props.onMessageChange(error.response.data.error);
    });                      
  }

  function validateInputs() {
    let titleValid_ = titleValid;
    let descriptionValid_ = descriptionValid;
    let durationValid_ = durationValid;
    let priceValid_ = priceValid;

    titleValid_ = title.length >= 6 && title.length <= 30;
    descriptionValid_ = description.length >= 12 && description.length <= 1000;
    priceValid_= price != '' && !isNaN(+price) && price>0;
    durationValid_ = duration != '' && !isNaN(+duration) && (duration % 1 === 0);

    setTitleValid(titleValid_);
    setDescriptionValid(descriptionValid_);
    setDurationValid(durationValid_);
    setPriceValid(priceValid_);

    return (titleValid_ && descriptionValid_ && durationValid_ && priceValid_);
  }

  function handleDelete(i){
    setTags(tags.filter((tag, index) => index !== i));
  }

  function handleAddition(tag){
    if(tag.text.length < 3 || tag.text.length > 15){
      setTagsValid(false);
      return;
    }   
    setTags([...tags, tag]);
    setTagsValid(true);
  }

  function handleDrag(tag, currPos, newPos){
    const newTags = tags.slice();

    newTags.splice(currPos, 1);
    newTags.splice(newPos, 0, tag);

    setTags(newTags);
  };

  function onClose(){
    setDataLoaded(false);

    setTitleValid(true);
    setDescriptionValid(true);
    setDurationValid(true);
    setPriceValid(true);
    setTagsValid(true);

    setTitle('');
    setDescription('');
    setDuration();
    setPrice();
    setTags([]);
    
    props.onAddOrEditCertificate(false, false, 0)
  }

    return (
      <Modal {...props} size="lg" centered>
        <Modal.Header className="modal-title">
          <Modal.Title id="add-edit-modal">
            {props.editCertificate ? "Edit certificate with ID = " + props.id : "Add New Certificate"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          
        <div className='form-wrapper'>
          <table class="form">
            <tr>
              <td>
                Title
              </td>
              <td>
                <input className="input" type="text" value={title} onChange={e => setTitle(e.target.value)} />
                {titleValid ? '' : <div className="errorMsg">Title field must not be less than 6 and greater than 30 characters</div>}
              </td>            
            </tr>
            <tr>
              <td>
                Description
              </td>
              <td>
                <textarea className="input" wrap='soft' rows="7" type="text" value={description} onChange={e => setDescription(e.target.value)}>
                </textarea>
                {descriptionValid ? '' : <div className="errorMsg">Description field must not be less than 12 and greater than 1000 characters</div>}
              </td>            
            </tr>
            <tr>
              <td>
                Duration
              </td>
              <td>
                <input className="input" type="text" value={duration} onChange={e => setDuration(e.target.value)} />
                {durationValid ? '' : <div className="errorMsg">Duration must be an integer number</div>}
              </td>            
            </tr>
            <tr>
              <td>
                Price
              </td>
              <td>
                <input className="input" type="text" value={price} onChange={e => setPrice(e.target.value)} />
                {priceValid ? '' : <div className="errorMsg">Price must be a number or float and be greater than 0</div>}
              </td>            
            </tr>
            <tr>
              <td>
                Tags
              </td>
              <td>
                <div>
                  <ReactTags
                        tags={tags}
                        suggestions={suggestions}
                        delimiters={delimiters}
                        handleDelete={handleDelete}
                        handleAddition={handleAddition}
                        handleDrag={handleDrag}
                        inputFieldPosition="bottom"
                        autocomplete
                        editable
                  />
                </div>
                {tagsValid ? '' : <div className="errorMsg">Tag name should be not less than 3 and greater than 15 characters</div>}
              </td>            
            </tr>
          </table>
        </div>

        </Modal.Body>
        <Modal.Footer>
          <Button className="submit-button" onClick={() => addOrEditCertificate(cookies.token)}>Save</Button>
          <Button className="cancel-button" onClick={() => onClose()}>
            Close
          </Button>
        </Modal.Footer> <br/>
      </Modal>
    );
}

export default AddOrEditCertificate;