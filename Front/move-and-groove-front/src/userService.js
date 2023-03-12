import Axios from "./callerService";

let getUser = (id) => {
	return Axios.get(`users/${id}`);
};

let deleteUser = (id) => {
	return Axios.delete(`users/${id}`);
};

let updateUser = (id, credentials) => {
	return Axios.put(`users/${id}`, credentials);
};

export const userService = {
	getUser,
	deleteUser,
	updateUser,
};
