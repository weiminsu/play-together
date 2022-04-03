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
                <div id="suburbDiv">
                      <label for="suburb">Suburb: </label>
                      <input type="text" id="suburb" value={account.suburb}></input>
                </div>
                <div id="descriptionDiv">
                      <label for="description">Description: </label>
                      <textarea id="description" value={account.description}></textarea>
                </div>
            </div>
        );
    }
}

export default withRouter(ProfilePage);
