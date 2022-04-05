import './ProfilePage.css';
import React from 'react';
import { useParams } from "react-router-dom";

export function withRouter(Children){
     return(props)=>{
        const match  = {params: useParams()};
        return <Children {...props}  match = {match}/>
    }
}

class ProfilePage extends React.Component {

    // Constructor
    constructor(props) {
        super(props);

        this.state = {
            account: null,
            dataLoaded: false,
            errorLoadingData: false
        };
    }

    componentDidMount() {
        fetch(`http://localhost:8080/account/${this.props.match.params.id}`, {crossDomain: true})
            .then(response => {
                if (response.ok) {
                    response.json()
                    .then((jsonResponse) => {
                        this.setState({
                            account: jsonResponse,
                            dataLoaded: true
                        });
                    })
                } else {
                     this.setState({
                         errorLoadingData: true
                     });
                    console.log(response)
                }
            })
    }

    render() {
        const { dataLoaded, account } = this.state;
        if (!dataLoaded) return (
            <div>
               <h1> Error Loading Data </h1>
            </div>
        );

        return (
            <div className = "App">
                <div id="profileImageDiv">
                      <img id="profileImage" src={account.profileImageUrl || process.env.PUBLIC_URL + "/default_profile_image.jpeg"} alt="No Profile Image" />
                </div>
                <div id="suburbDiv" class="form-floating mb-3">
                      <input type="text" id="suburb" class="form-control" value={account.suburb} placeholder="Enter a suburb"></input>
                      <label for="suburb">Suburb: </label>
                </div>
                <div id="descriptionDiv" class="form-floating mb-3">
                      <textarea id="description" class="form-control" value={account.description} placeholder="Enter some description"></textarea>
                      <label for="description">Description: </label>
                </div>
                <div id="childrenDiv">
                    <ul class="list-group child-list">
                        {
                            account.children.map( (child) => {
                                return (
                                    <li class="list-group-item">
                                        <div class="card childCard">
                                          <div class="card-body">
                                            <h5 class="card-title">{child.name}</h5>
                                            <h6 class="card-subtitle mb-2 text-muted">{child.age} year old</h6>
                                            <p class="card-text">Some description</p>
                                          </div>
                                        </div>
                                    </li>
                                )
                            })
                        }
                        <li class="list-group-item">
                            <div class="card childCard">
                              <div class="card-body">
                                <h5 class="card-title">Add a new child</h5>
                                <div class="form-floating mb-3">
                                      <input type="text" id="add-a-child-name" class="form-control" placeholder="Child Name"></input>
                                      <label for="add-a-child-name">Child Name: </label>
                                </div>
                                <div class="form-floating mb-3">
                                       <input type="text" id="add-a-child-age" class="form-control" placeholder="Child Age"></input>
                                      <label for="add-a-child">Child Age: </label>
                                </div>
                                <a href="#" class="btn btn-primary">Add</a>
                              </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        );
    }
}

export default withRouter(ProfilePage);
