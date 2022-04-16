import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import { PrivateRoute } from './PrivateRoute';
import './index.css';
import ProfilePage from './profile/ProfilePage';
import Login from './login/Login';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
    <Router>
      <Routes>
          <Route index element={<PrivateRoute><ProfilePage /></PrivateRoute>} />
          <Route path="/login" element={<Login />} />
          <Route path="/account/:id" element={<PrivateRoute><ProfilePage /></PrivateRoute>} />
      </Routes>
    </Router>,

    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
