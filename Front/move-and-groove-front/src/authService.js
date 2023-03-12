import Axios from "./callerService";

let login = (credentials) => {
	return Axios.post("auth/authentication", credentials);
};

let signUp = (credentials) => {
	return Axios.post("auth/register", credentials);
};

let saveToken = (token) => {
	localStorage.setItem("token", token);
};

let logout = () => {
	localStorage.removeItem("token");
};

let isLogged = () => {
	let token = localStorage.getItem("token");
	return !!token;
};

let getToken = () => {
	return localStorage.getItem("token");
};

export const authService = {
	login,
	signUp,
	saveToken,
	logout,
	isLogged,
	getToken,
};
