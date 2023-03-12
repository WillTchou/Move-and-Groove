import axios from "axios";
import { authService } from "./authService";
import jwt_decode from "jwt-decode";

const Axios = axios.create({
	baseURL: "http://localhost:8080/api/v1/",
});

let getUsernameFromToken = () => {
	return jwt_decode(authService.getToken()).sub;
};

Axios.interceptors.request.use((req) => {
	if (authService.isLogged()) {
		req.headers.Authorization = "Bearer " + authService.getToken();
		req.headers.username = getUsernameFromToken();
	}
	return req;
});

export default Axios;
