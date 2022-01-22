import axios from 'axios';
import authHeader from './auth-header';
import {API_BASE_URL} from './apiBaseUrl';

const API_URL = API_BASE_URL;

class UserService {

    getAllClientsForDietitianUrl(dietitianUsername) {
        return API_URL + 'dietitian/clients/' + dietitianUsername;
    }

    getAllDietitianUrl() {
        return API_URL + 'dietitian/all';
    }

    activateUser(username) {
        return axios({
            method: 'put',
            url: API_URL + 'user/activate/' + username,
            headers: authHeader()
        })
    }

    assignClientToDietitian(client_username, dietitian_username) {
        return axios({
            method: 'post',
            url: API_URL + 'client/associate',
            data: {
                dietitianUsername: dietitian_username,
                clientUsername: client_username,
            },
            headers: authHeader()
        })
    }

    registerClientByCs(requestData) {
        return axios({
            method: 'post',
            url: API_URL + 'user/create',
            data: requestData,
            headers: authHeader()
        })
    }

    registerClient(requestData) {
        return axios({
            method: 'post',
            url: API_URL + 'user/register',
            data: requestData,
            headers: authHeader()
        })
    }

    getUserinfo(id) {
        return axios({
            method: 'get',
            url: API_URL + 'user/' + id,
            headers: authHeader()
        }).then((response) => {
            return response.data
        })
    }
}

export default new UserService();