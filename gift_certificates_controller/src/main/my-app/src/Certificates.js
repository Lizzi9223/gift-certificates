import React from "react";
import axios from "axios";
import { format, parseISO } from "date-fns";
import { instanceOf } from "prop-types";
import { withCookies, Cookies } from "react-cookie";

import Pagination from '@vlsergey/react-bootstrap-pagination';
import 'bootstrap/dist/css/bootstrap.min.css';  
import './css/MainPage.css';

class Certificates extends React.Component{
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props){
        super(props)
        this.state ={
            certificates:[],
            recordPerPage:10,
            totalPages:0,
            totalElements:0,
            creationDateSort:"desc",
            nameSort:"desc",
            creationDateSortAngle:0,
            nameSortAngle:0,
            sortParams:"",
            searchParams: ""
        }
    }

    componentDidMount(){      
        this.state.sortParams = "&sort=create_date," + this.state.creationDateSort + "&sort=name," + this.state.nameSort;
        const { cookies } = this.props; 
        this.getCertificatesByPagination(cookies.get("page"));
    }

    componentWillReceiveProps = (nextProps) => {
        if (nextProps.searchParams !== this.props.searchParams) {
            const { cookies } = this.props;
            this.getCertificatesByPagination(cookies.get("page"));
        }
      }

    getCertificatesByPagination(currentPage){
        var searchParams = "";
        var isAmpersandSet = false;
        if(this.props.searchParams.name != null && this.props.searchParams.name !== ""){
            searchParams += "name=";
            searchParams += this.props.searchParams.name;
            isAmpersandSet = true;
        }
        if(this.props.searchParams.tags!= null && this.props.searchParams.tags.length!=0){
            if(isAmpersandSet) searchParams += "&";
            searchParams += "tagNames=";
            for(let i=0; i < this.props.searchParams.tags.length; i++){
                searchParams += this.props.searchParams.tags[i];
                searchParams += ",";
            }
            searchParams = searchParams.slice(0, -1);
        }

        if(searchParams !== "") searchParams += "&";

        this.setState({searchParams : searchParams});

        const getCertificates = async () => {
            await axios
                .get("certificate?" + searchParams + "page=" + currentPage + "&size=" + this.state.recordPerPage + this.state.sortParams)
                .then(response => response.data)
                .then((data) =>{
                    if(data.content)
                        this.setState({
                            certificates:data.content,
                            totalPages:data.totalPages,
                            totalElements: data.totalElements,                            
                            recordPerPage: data.size
                        });
                })
                .catch((error) => {
                    this.props.onMessageChange(error.response.data.errorMessage);
                });
        };
        getCertificates();        
    } 

    handleChangePage = (e) =>{
        const { cookies } = this.props;        
        cookies.set("page", e.target.value, { path: "/" });
        this.getCertificatesByPagination(cookies.get("page"));
    }

    handleChangeSize = (e) =>{
        this.state.recordPerPage = e.target.value;
        const { cookies } = this.props;
        this.getCertificatesByPagination(cookies.get("page"));
    }

    sortByCreateDate = () =>{
        if(this.state.creationDateSort == "desc"){
            this.state.creationDateSortAngle = 180;
            this.state.creationDateSort = "asc";
        }            
        else {
            this.state.creationDateSortAngle = 0;
            this.state.creationDateSort = "desc";
        }
        this.state.sortParams = "&sort=create_date," + this.state.creationDateSort + "&sort=name," + this.state.nameSort;
        const { cookies } = this.props;
        this.getCertificatesByPagination(cookies.get("page"));
    }

    sortByName = () =>{
        if(this.state.nameSort == "desc"){
            this.state.nameSortAngle = 180;
            this.state.nameSort = "asc";
        }            
        else{
            this.state.nameSortAngle = 0;
            this.state.nameSort = "desc";
        }
        this.state.sortParams = "&sort=name," + this.state.nameSort + "&sort=create_date," + this.state.creationDateSort;
        const { cookies } = this.props;
        this.getCertificatesByPagination(cookies.get("page"));
    }

    render(){  
        const {certificates} = this.state;
        const { cookies } = this.props;
        return(
            <div className="certificate-table">
                <table>
                    <thead>
                        <th onClick={this.sortByCreateDate}>
                            <img class="sortImage" 
                                style={{"transform":"rotate("+this.state.creationDateSortAngle+"deg)"}} 
                                src={require('./images/arrow_drop_down.png')} />Datetime
                        </th>
                        <th onClick={this.sortByName}>
                            <img class="sortImage" 
                                style={{"transform":"rotate("+this.state.nameSortAngle+"deg)"}}  
                                src={require('./images/arrow_drop_down.png')} />Title
                        </th>
                        <th>Tags</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </thead>
                    <tbody>
                        {certificates.length===0 
                            ? <tr align="center"><td colSpan="6" style={{"padding" : "25px"}}>No Certificates Found</td></tr>
                            : certificates.map(
                                (certificates,index) =>(                                    
                                    <tr key = {certificates.id}>
                                        <td>
                                            {
                                                format(parseISO(certificates.createDate), 'yyyy-MM-dd H:mm')
                                            }
                                        </td>
                                        <td>{certificates.name}</td>
                                        <td align="center">
                                            {
                                                certificates.tags.length===0
                                                ? <span>—</span>
                                                : certificates.tags.map((tags, index) => (
                                                    <span>
                                                        {tags.name}
                                                        {
                                                            index+1==certificates.tags.length 
                                                                ? "" 
                                                                : <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>

                                                        }
                                                    </span>
                                                ))
                                            }
                                        </td>
                                        <td>{certificates.description}</td>
                                        <td>{certificates.price}</td>
                                        <td className="buttons" align="center">
                                            <button style={{"background" : "#0000CD"}}>View</button>
                                            <button 
                                                onClick={() => this.props.onAddOrEditCertificate(true, true, certificates.id)}
                                                style={{"background" : "#FFA500"}}
                                            >
                                                Edit
                                            </button>
                                            <button 
                                                onClick={() => this.props.onDeleteCertificate(true, certificates.id)} 
                                                style={{"background" : "#B22222"}}
                                            >
                                                    Delete
                                            </button>
                                        </td>
                                    </tr>
                                )
                            )
                        }
                    </tbody>
                </table>
                
                <div className="pagination">
                    <Pagination 
                        aroundCurrent={4} 
                        showFirstLast={true} 
                        showPrevNext={true} 
                        atBeginEnd={0} 
                        value={cookies.get("page")}
                        totalPages={this.state.totalPages} 
                        onChange={e => this.handleChangePage(e)}/>
                </div>

                <div className="combobox" onChange={e => this.handleChangeSize(e)} value={this.state.recordPerPage}>
                    <select className="select">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                    </select>
                </div>
            </div>
        )
    }
}

export default withCookies(Certificates);