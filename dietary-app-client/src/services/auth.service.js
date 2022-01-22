import axios from 'axios';

import {API_BASE_URL} from './apiBaseUrl';


class AuthService {

    login(user) {
        return axios
            .post(API_BASE_URL + 'auth/authenticate', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_BASE_URL + 'user/register', {
            username: user.username,
            password: user.password
        });
    }

}

export default new AuthService();