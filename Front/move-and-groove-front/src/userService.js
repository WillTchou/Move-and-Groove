import Axios from "./callerService";

let getUserById = (id) => {
	return Axios.get(`users/${id}`);
};

let getUserByUsername = (username) => {
	return Axios.get(`users?username=${username}`);
};

let deleteUser = (id) => {
	return Axios.delete(`users/${id}`);
};

let updateUser = (id, credentials) => {
	return Axios.put(`users/${id}`, credentials);
};

export const userService = {
	getUserById,
	getUserByUsername,
	deleteUser,
	updateUser,
};
