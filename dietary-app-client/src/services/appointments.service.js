import axios from 'axios';
import {API_BASE_URL} from './apiBaseUrl';
import authHeader from "./auth-header";

const API_URL = API_BASE_URL + 'appointments/';

class AppointmentsService {

     getAllAppointments() {
        return axios({
            method: 'get',
            url: API_URL + 'all',
            headers: authHeader()
        })
            .then((response) => {
                return response.data
            });
    }

    getAllAppointmentsForDietitian(dietitianId) {
        return  axios({
            method: 'get',
            url: API_URL + 'dietitian/' + dietitianId,
            headers: authHeader()
        })  .then((response) => {
            return response.data
        });
    }

    getAllAppointmentsForClients(clientId) {
        return axios({
            method: 'get',
            url: API_URL + 'client/' + clientId,
            headers: authHeader()
        }).then((response) => {
            return response.data
        });
    }

    createAppointment(appointment) {
        return axios({
            method: 'POST',
            url: API_URL + 'create',
            data: appointment,
            headers: authHeader()
        })
    }

    updateAppointment(appointment) {
        return axios({
            method: 'POST',
            url: API_URL + 'update',
            data: appointment,
            headers: authHeader()
        })
    }

    removeAppointment(removedEventId) {
        return axios({
            method: 'DELETE',
            url: API_URL + 'delete/' + removedEventId,
            headers: authHeader()
        })
    }

}

export default new AppointmentsService();