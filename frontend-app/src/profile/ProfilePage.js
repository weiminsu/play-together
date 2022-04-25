import './ProfilePage.css';
import React from 'react';
import { useParams } from "react-router-dom";
import { v4 as uuidv4 } from 'uuid';

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
        console.log(`${process.env.REACT_APP_SERVER_ENDPOINT}/account/${this.props.match.params.id}`)
        fetch(`${process.env.REACT_APP_SERVER_ENDPOINT}/account/${this.props.match.params.id}`, {crossDomain: true})
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

    addAChild(state) {

        const account = state.account
        const children = account.children

        var nameInput = document.getElementById("add-a-child-name")
        var ageInput = document.getElementById("add-a-child-age")
        var newChild = {}
        newChild.id = uuidv4()
        newChild.name = nameInput.value
        newChild.age = parseInt(ageInput.value)
        newChild.accountId = account.accountId

        children.push(newChild)
        state.account.children = children

        nameInput.value = null
        ageInput.value = null
    }

    saveAndPostData(state) {
        var suburbInput = document.getElementById("suburb")
        var descriptionInput = document.getElementById("description")
        state.account.suburb = suburbInput.value
        state.account.description = descriptionInput.value


        fetch(`${process.env.REACT_APP_SERVER_ENDPOINT}/account/`, {
          method: 'POST',
          credentials: 'include',
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(state.account)
        })
    }

    getProfileImageUrl(imageId) {
        fetch(`${process.env.REACT_APP_SERVER_ENDPOINT}/image/${imageId}`, {crossDomain: true})
            .then(response => {
                if (response.ok) {
                    response.json()
                    .then((jsonResponse) => {
                        this.setProfileImageUrl(jsonResponse.imageUrl)
                    })
                } else {
                    console.log("failed to get profile image url")
                    console.log(response)
                }
            })
    }

    setProfileImageUrl(src) {
        var profileImage = document.getElementById("profileImage")
        profileImage.src = src
    }

    getProfileImageUploadUrl() {
        var uploadUrlResponse = null

        return uploadUrlResponse;
    }

    chooseImageAndUpload(state) {

        var input = document.createElement('input')
        input.type = 'file'

        input.onchange = e => {
           // getting a hold of the file reference
           var file = e.target.files[0]
           console.log(file.name)
           console.log(file.type)
           let fileType = file.type.substring(file.type.indexOf("/") + 1)
           // setting up the reader
           let formData = new FormData();
           formData.append('file', file);

           fetch(`${process.env.REACT_APP_SERVER_ENDPOINT}/image/upload-url?imageType=${fileType}`, {crossDomain: true})
               .then(response => {
                   if (response.ok) {
                       response.json()
                       .then((jsonResponse) => {
                          var uploadUrlResponse = jsonResponse
                          fetch(uploadUrlResponse.uploadUrl, {
                              method: 'PUT',
                              headers: {
                                  "Content-length": file.size
                              },
                              body: file // Here, stringContent or bufferContent would also work
                          })
                          .then(function(res) {
                              state.account.profileImageUrl = uploadUrlResponse.imageId
                              this.getProfileImageUrl(uploadUrlResponse.imageId)
                              console.log(res.json())
                          }).then(function(json) {
                              console.log(json)
                          });
                       })
                   } else {
                       console.log("failed to get upload image url")
                       console.log(response)
                   }
               })
        }

        input.click()
    }

    render() {
        const { dataLoaded, account } = this.state;
        if (!dataLoaded) return (
            <div>
               <h1> Still Loading Data </h1>
            </div>
        );

        return (
            <div className = "App">
                <div id="profileImageDiv">
                      <img id="profileImage" src={this.getProfileImageUrl(account.profileImageUrl) || process.env.PUBLIC_URL + "/default_profile_image.jpeg"} alt="No Profile Image" onClick={() => this.chooseImageAndUpload(this.state)}/>
                </div>
                <div id="suburbDiv" class="form-floating mb-3">
                      <input type="text" id="suburb" class="form-control" defaultValue={account.suburb} placeholder="Enter a suburb"></input>
                      <label for="suburb">Suburb: </label>
                </div>
                <div id="descriptionDiv" class="form-floating mb-3">
                      <textarea id="description" class="form-control" defaultValue={account.description} placeholder="Enter some description"></textarea>
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
                                    <a href="#" class="btn btn-primary" onClick={() => this.addAChild(this.state)}>Add</a>
                              </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div id="save-button">
                    <a href="#" class="btn btn-primary" onClick={() => this.saveAndPostData(this.state)}>Save</a>
                </div>
            </div>
        );
    }
}

export default withRouter(ProfilePage);
